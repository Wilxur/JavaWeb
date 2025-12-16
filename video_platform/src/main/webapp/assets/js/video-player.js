document.addEventListener("DOMContentLoaded", () => {
    const video = document.getElementById("mainVideo");

    const originalVideoSrc = video.src;

    let hasInsertedAd = false;
    let resumeTime = 0;

    const AD_INSERT_TIME = 10;

    function sendLog(videoId, eventType) {
        fetch("log", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `videoId=${videoId}&eventType=${eventType}`
        });
    }


    // 插播逻辑
    video.addEventListener("timeupdate", async () => {
        if (!hasInsertedAd && video.currentTime >= AD_INSERT_TIME) {
            hasInsertedAd = true;
            resumeTime = video.currentTime;

            video.pause();

            const resp = await fetch("ad");
            const ad = await resp.json();

            video.src = ad.adVideoUrl;
            video.load();
            video.play();

            video.onended = () => {
                video.onended = null;
                video.src = originalVideoSrc;
                video.load();
                video.currentTime = resumeTime;
                video.play();
            };
        }
    });

    // ⭐ 主视频完整播放结束后的状态重置
    video.addEventListener("ended", () => {
        // 确保是“正片结束”，不是广告结束
        if (video.src === originalVideoSrc) {
            hasInsertedAd = false;
            resumeTime = 0;
            video.currentTime = 0;
        }
    });
});
