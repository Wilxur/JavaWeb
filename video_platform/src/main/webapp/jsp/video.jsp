<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${video.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<div class="container">

    <div class="topbar">
        <div class="brand">${video.title}</div>
        <div class="nav">
            <a class="btn" href="${pageContext.request.contextPath}/videos">返回列表</a>
        </div>
    </div>

    <div class="card">
        <!-- 播放器区域：广告与正片同一位置 -->
        <div class="player">

            <!-- 正片视频（底层） -->
            <video
                    id="mainVideo"
                    class="video"
                    data-video-id="${video.id}"
                    controls
                    preload="metadata"
                    src="${pageContext.request.contextPath}/media?path=${video.filePath}">
                您的浏览器不支持 video 标签
            </video>

            <!-- 广告层（覆盖在正片上方） -->
            <div id="adOverlay" class="ad-overlay" aria-hidden="true">
                <div class="ad-topbar">
                    <span class="ad-badge">广告</span>
                    <div class="ad-actions">
                        <span id="adCountdown" class="ad-countdown">加载中...</span>
                        <button id="adSkipBtn" class="ad-skip" type="button" disabled>跳过</button>
                    </div>
                </div>

                <!-- 广告媒体（JS 动态注入 video/img） -->
                <div id="adMediaHost" class="ad-media-host"></div>

                <!-- 自动播放失败时的提示 -->
                <button id="adPlayBtn" class="ad-play-btn" type="button" style="display:none;">
                    点击播放广告
                </button>
            </div>

        </div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/js/video-player.js"></script>
</body>
</html>
