function sendLog(videoId, eventType) {
    fetch("log", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: `videoId=${encodeURIComponent(videoId)}&eventType=${encodeURIComponent(eventType)}`
    });
}

// ===== 模拟广告接口（测试阶段用）=====
async function fetchAd() {
    // 测试用：随机返回视频 / 图片广告
    const ads = [
        {
            type: "video",
            url: "http://10.100.164.17:8080/ad-platform/static/ad-videos/ad1.mp4",
            duration: 5
        },
        {
            type: "image",
            url: "http://10.100.164.17:8080/ad-platform/static/ad-images/ad1.jpg",
            duration: 5
        }
    ];
    return ads[Math.floor(Math.random() * ads.length)];
}

document.addEventListener("DOMContentLoaded", async () => {
    const video = document.getElementById("mainVideo");
    const adBox = document.getElementById("ad-container");
    if (!video || !adBox) return;

    const videoId = video.dataset.videoId;

    // 先暂停正片
    video.pause();

    // 拉广告
    const ad = await fetchAd();

    // 广告开始日志
    sendLog(videoId, "AD_START");

    // ===== 根据广告类型渲染 =====
    if (ad.type === "video") {
        const adVideo = document.createElement("video");
        adVideo.src = ad.url;
        adVideo.autoplay = true;
        adVideo.muted = false;
        adVideo.playsInline = true;
        adVideo.controls = false;

        adBox.appendChild(adVideo);

        adVideo.onended = () => {
            finishAd(videoId, adBox, video);
        };

    } else if (ad.type === "image") {
        const img = document.createElement("img");
        img.src = ad.url;
        adBox.appendChild(img);

        setTimeout(() => {
            finishAd(videoId, adBox, video);
        }, ad.duration * 1000);
    }
});

// ===== 广告结束统一处理 =====
function finishAd(videoId, adBox, video) {
    sendLog(videoId, "AD_END");

    // 清空广告
    adBox.innerHTML = "";

    // 播放正片
    video.play();
    sendLog(videoId, "VIDEO_START");
}
