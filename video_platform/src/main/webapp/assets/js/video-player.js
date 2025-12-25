// ===== 工具函数 =====
function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

// ===== 分类映射（中文→英文）=====
const CATEGORY_MAP = {
    '电子产品': 'electronics',
    '服装鞋帽': 'clothing',
    '食品饮料': 'food',
    '美妆护肤': 'beauty',
    '家居用品': 'home',
    '运动户外': 'sports'
};

function getMappedCategory() {
    const videoCategory = document.getElementById('mainVideo')?.dataset.category || 'electronics';
    return CATEGORY_MAP[videoCategory] || 'electronics';
}

// ===== 日志上报（严格按广告平台要求）=====
function sendLog(videoId, eventType) {
    const uid = getCookie('ad_platform_uid');
    if (!uid || !window.currentAd) return; // 必须有当前广告

    // 广告曝光上报（仅 AD_START）
    if (eventType === "AD_START") {
        const img = new Image();
        img.src =
            `http://10.100.164.17:8080/ad-platform/api/track/impression?` +
            `uid=${uid}` +
            `&adId=${window.currentAd.adId}` +
            `&site=video` +
            `&category=${getMappedCategory()}`;
        img.style.display = "none";
        document.body.appendChild(img);
    }

    // 原视频站日志（可保留）
    fetch("log", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `videoId=${encodeURIComponent(videoId)}&eventType=${encodeURIComponent(eventType)}`
    });
}

// ===== 广告获取接口 =====
async function fetchAd() {
    const uid = getCookie('ad_platform_uid');
    const category = getMappedCategory();

    try {
        const response = await fetch(
            `http://10.100.164.17:8080/ad-platform/api/ad/get?` +
            `uid=${uid}&category=${category}&site=video`,
            { credentials: 'include' }
        );

        if (!response.ok) return null;

        const data = await response.json();
        if (!data.success || !data.ad) return null;

        const ad = data.ad;
        return {
            type: ad.adType,      // video / image
            url: ad.content,
            duration: ad.adType === 'video' ? 10 : 5,
            adId: ad.id
        };

    } catch (e) {
        console.error("广告获取失败", e);
        return null;
    }
}

// ===== 广告点击上报 =====
function trackAdClick(adId, redirectUrl) {
    const uid = getCookie('ad_platform_uid');
    const category = getMappedCategory();

    fetch(
        `http://10.100.164.17:8080/ad-platform/api/track/click?` +
        `uid=${uid}&adId=${adId}&site=video&category=${category}` +
        `&redirect=${encodeURIComponent(redirectUrl || '')}`,
        { credentials: 'include' }
    );
}

// ===== 主逻辑 =====
document.addEventListener("DOMContentLoaded", async () => {
    const mainVideo = document.getElementById("mainVideo");
    const overlay = document.getElementById("adOverlay");
    const host = document.getElementById("adMediaHost");
    const countdownEl = document.getElementById("adCountdown");
    const skipBtn = document.getElementById("adSkipBtn");
    const playBtn = document.getElementById("adPlayBtn");

    if (!mainVideo) return;

    const videoId = mainVideo.dataset.videoId;

    mainVideo.pause();
    mainVideo.controls = false;

    const ad = await fetchAd();
    if (!ad) {
        mainVideo.controls = true;
        mainVideo.play().catch(() => {});
        return;
    }

    // 保存当前广告，供 sendLog 使用
    window.currentAd = ad;

    overlay.classList.add("is-active");

    sendLog(videoId, "AD_START");

    const SKIP_AFTER = 5;
    let remaining = ad.duration;
    let timer = setInterval(() => {
        countdownEl.textContent = `剩余 ${remaining}s`;
        skipBtn.disabled = (ad.duration - remaining) < SKIP_AFTER;
        remaining--;
        if (remaining < 0) endAd();
    }, 1000);

    function endAd() {
        clearInterval(timer);
        overlay.classList.remove("is-active");
        host.innerHTML = "";
        mainVideo.controls = true;
        mainVideo.play().catch(() => {});
        sendLog(videoId, "AD_END");
        sendLog(videoId, "VIDEO_START");
    }

    skipBtn.onclick = () => {
        if (!skipBtn.disabled) endAd();
    };

    if (ad.type === "video") {
        const v = document.createElement("video");
        v.src = ad.url;
        v.autoplay = true;
        v.muted = true;
        v.onended = endAd;
        host.appendChild(v);
        v.play().catch(() => {
            playBtn.style.display = "block";
            playBtn.onclick = () => {
                playBtn.style.display = "none";
                v.muted = false;
                v.play().catch(() => {});
            };
        });
    } else {
        const img = document.createElement("img");
        img.src = ad.url;
        img.onclick = () => {
            trackAdClick(ad.adId, ad.url);
            window.open(ad.url, '_blank');
        };
        host.appendChild(img);
    }
});