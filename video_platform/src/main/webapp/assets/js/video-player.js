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

// ===== 日志上报（调用广告平台接口） =====
function sendLog(videoId, eventType) {
    const uid = getCookie('ad_platform_uid');
    if (!uid) return; // 无UID不上报

    fetch("http://10.100.164.17:8080/ad-platform/api/track/impression", {
        method: "GET",
        credentials: "include"
    });

    // 原视频站日志可保留作为备份
    fetch("log", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `videoId=${encodeURIComponent(videoId)}&eventType=${encodeURIComponent(eventType)}`
    });
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

// ===== 广告获取接口（核心）=====
async function fetchAd() {
    const uid = getCookie('ad_platform_uid');
    const category = getMappedCategory();

    // ★★★ 关键1：添加 site=video 参数
    // ★★★ 关键2：credentials: 'include' 携带Cookie
    try {
        const response = await fetch(
            `http://10.100.164.17:8080/ad-platform/api/ad/get?` +
            `uid=${uid}&category=${category}&site=video`,
            { credentials: 'include' }
        );

        if (!response.ok) {
            console.error('广告API请求失败:', response.status);
            return null;
        }

        const data = await response.json();

        if (!data.success || !data.ad) {
            console.warn('暂无可用广告:', data.message);
            return null; // 返回null表示直接播放正片
        }

        // 适配广告数据结构
        const ad = data.ad;
        return {
            type: ad.adType,      // 'video' 或 'image'
            url: ad.content,      // 视频/图片URL
            duration: ad.adType === 'video' ? 10 : 5, // 视频10秒，图片5秒（可让后端返回duration字段）
            adId: ad.id           // 用于日志上报
        };

    } catch (err) {
        console.error('广告加载异常:', err);
        return null; // 出错也直接播放正片，不影响用户体验
    }
}

// ===== 广告点击上报（新增）=====
function trackAdClick(adId, redirectUrl) {
    const uid = getCookie('ad_platform_uid');
    const category = getMappedCategory();

    fetch(`http://10.100.164.17:8080/ad-platform/api/track/click?` +
        `uid=${uid}&adId=${adId}&site=video&category=${category}&redirect=${encodeURIComponent(redirectUrl || '')}`,
        { credentials: 'include' }
    );
}

// ===== 主逻辑（保持原有结构，仅调用fetchAd）=====
document.addEventListener("DOMContentLoaded", async () => {
    const mainVideo = document.getElementById("mainVideo");
    const overlay = document.getElementById("adOverlay");
    const host = document.getElementById("adMediaHost");
    const countdownEl = document.getElementById("adCountdown");
    const skipBtn = document.getElementById("adSkipBtn");
    const playBtn = document.getElementById("adPlayBtn");

    if (!mainVideo || !overlay || !host || !countdownEl || !skipBtn || !playBtn) return;

    const videoId = mainVideo.dataset.videoId;

    // ★★★ 正片先暂停
    mainVideo.pause();
    mainVideo.controls = false;

    // 拉取广告
    const ad = await fetchAd();

    if (!ad) {
        // 无广告，直接播放正片
        mainVideo.controls = true;
        mainVideo.play().catch(() => {});
        sendLog(videoId, "VIDEO_START");
        return;
    }

    // 展示广告层
    overlay.classList.add("is-active");
    overlay.setAttribute("aria-hidden", "false");

    // 广告开始日志
    sendLog(videoId, "AD_START");

    // 跳过策略和倒计时逻辑（保持原有）
    const SKIP_AFTER = 5;
    let remaining = Math.max(1, Number(ad.duration));
    let timer = null;
    let adEnded = false;

    function setCountdownText() {
        countdownEl.textContent = `剩余 ${remaining}s`;
        const canSkip = (ad.duration - remaining) >= SKIP_AFTER;
        skipBtn.disabled = !canSkip;
    }

    function endAdAndPlayMain() {
        if (adEnded) return;
        adEnded = true;
        clearInterval(timer);

        sendLog(videoId, "AD_END");
        host.innerHTML = "";
        playBtn.style.display = "none";

        overlay.classList.remove("is-active");
        overlay.setAttribute("aria-hidden", "true");

        mainVideo.controls = true;
        mainVideo.play().catch(() => {});
        sendLog(videoId, "VIDEO_START");
    }

    skipBtn.addEventListener("click", () => {
        if (!skipBtn.disabled) endAdAndPlayMain();
    });

    // 渲染广告（视频/图片）
    if (ad.type === "video") {
        const adVideo = document.createElement("video");
        adVideo.src = ad.url;
        adVideo.playsInline = true;
        adVideo.controls = false;
        adVideo.muted = true; // 先静音尝试自动播放
        adVideo.autoplay = true;

        host.appendChild(adVideo);

        setCountdownText();
        timer = setInterval(() => {
            remaining = Math.max(0, remaining - 1);
            setCountdownText();
            if (remaining <= 0) endAdAndPlayMain();
        }, 1000);

        adVideo.addEventListener("ended", endAdAndPlayMain);

        // 自动播放失败处理
        adVideo.play().catch(() => {
            playBtn.style.display = "block";
            playBtn.onclick = () => {
                playBtn.style.display = "none";
                adVideo.muted = false;
                adVideo.play().catch(() => {});
            };
        });
    } else {
        // 图片广告
        const img = document.createElement("img");
        img.src = ad.url;
        img.alt = "广告";
        img.style.cursor = "pointer";
        img.onclick = () => trackAdClick(ad.adId, ad.url); // 图片点击上报
        host.appendChild(img);

        setCountdownText();
        timer = setInterval(() => {
            remaining = Math.max(0, remaining - 1);
            setCountdownText();
            if (remaining <= 0) endAdAndPlayMain();
        }, 1000);
    }
});