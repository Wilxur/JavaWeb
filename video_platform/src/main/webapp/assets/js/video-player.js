function sendLog(videoId, eventType) {
    fetch("log", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `videoId=${encodeURIComponent(videoId)}&eventType=${encodeURIComponent(eventType)}`
    });
}

// ===== 测试阶段：模拟广告接口（支持视频/图片）=====
async function fetchAd() {
    const ads = [
        { type: "video", url: "http://10.100.164.17:8080/ad-platform/static/ad-videos/ad1.mp4", duration: 8 },
        { type: "image", url: "http://10.100.164.17:8080/ad-platform/static/ad-images/ad1.jpg", duration: 5 }
    ];
    return ads[Math.floor(Math.random() * ads.length)];
}

document.addEventListener("DOMContentLoaded", async () => {
    const mainVideo = document.getElementById("mainVideo");
    const overlay = document.getElementById("adOverlay");
    const host = document.getElementById("adMediaHost");
    const countdownEl = document.getElementById("adCountdown");
    const skipBtn = document.getElementById("adSkipBtn");
    const playBtn = document.getElementById("adPlayBtn");

    if (!mainVideo || !overlay || !host || !countdownEl || !skipBtn || !playBtn) return;

    const videoId = mainVideo.dataset.videoId;

    // 正片先不让用户操作（避免用户先点正片）
    mainVideo.pause();
    mainVideo.controls = false;

    // 展示广告层（同一位置覆盖）
    overlay.classList.add("is-active");
    overlay.setAttribute("aria-hidden", "false");

    // 拉广告
    const ad = await fetchAd();

    // 广告开始日志
    sendLog(videoId, "AD_START");

    // 跳过策略：第 5 秒后可跳过（你可以改成 0 表示立即可跳）
    const SKIP_AFTER = 5;
    let remaining = Math.max(1, Number(ad.duration || 5));
    let timer = null;
    let adEnded = false;

    function setCountdownText() {
        countdownEl.textContent = `剩余 ${remaining}s`;
        const canSkip = (Number(ad.duration || remaining) - remaining) >= SKIP_AFTER;
        skipBtn.disabled = !canSkip;
    }

    function endAdAndPlayMain() {
        if (adEnded) return;
        adEnded = true;

        clearInterval(timer);
        timer = null;

        // 广告结束日志
        sendLog(videoId, "AD_END");

        // 清理广告媒体
        host.innerHTML = "";
        playBtn.style.display = "none";

        // 隐藏覆盖层
        overlay.classList.remove("is-active");
        overlay.setAttribute("aria-hidden", "true");

        // 恢复正片
        mainVideo.controls = true;

        // 播放正片（自动播放可能仍受限；受限则用户点播放即可）
        const p = mainVideo.play();
        if (p && typeof p.catch === "function") {
            p.catch(() => {});
        }

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

        // 为了尽量通过浏览器自动播放限制：先静音自动播
        adVideo.muted = true;
        adVideo.autoplay = true;

        host.appendChild(adVideo);

        // 倒计时：以 duration 为准；若广告视频更短，会提前结束
        setCountdownText();
        timer = setInterval(() => {
            remaining = Math.max(0, remaining - 1);
            setCountdownText();
            if (remaining <= 0) endAdAndPlayMain();
        }, 1000);

        adVideo.addEventListener("ended", endAdAndPlayMain);

        // 如果自动播放失败，提示用户点击播放广告
        const tryPlay = adVideo.play();
        if (tryPlay && typeof tryPlay.catch === "function") {
            tryPlay.catch(() => {
                playBtn.style.display = "block";
                playBtn.onclick = async () => {
                    playBtn.style.display = "none";
                    try {
                        // 用户交互后可以带声播放
                        adVideo.muted = false;
                        await adVideo.play();
                    } catch (e) {
                        // 仍失败则保持静音播放
                        adVideo.muted = true;
                        await adVideo.play().catch(() => {});
                    }
                };
            });
        }
    } else {
        // image
        const img = document.createElement("img");
        img.src = ad.url;
        img.alt = "广告";
        host.appendChild(img);

        setCountdownText();
        timer = setInterval(() => {
            remaining = Math.max(0, remaining - 1);
            setCountdownText();
            if (remaining <= 0) endAdAndPlayMain();
        }, 1000);
    }
});
