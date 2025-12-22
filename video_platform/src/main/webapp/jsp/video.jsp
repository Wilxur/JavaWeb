<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${video.title}</title>

    <!-- 页面样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<div class="container">

    <!-- 顶部栏 -->
    <div class="topbar">
        <div class="brand">${video.title}</div>
        <div class="nav">
            <a class="btn" href="${pageContext.request.contextPath}/videos">返回列表</a>
        </div>
    </div>

    <!-- 播放区域 -->
    <div class="card video-wrap">

        <!-- ===== 广告区域（可展示视频 / 图片广告）===== -->
        <div id="ad-container" class="ad-box">
            <!-- 广告内容由 video-player.js 动态注入 -->
        </div>

        <!-- ===== 正片视频 ===== -->
        <video
                id="mainVideo"
                class="video"
                data-video-id="${video.id}"
                controls
                preload="metadata"
                src="${pageContext.request.contextPath}/media?path=${video.filePath}">
            您的浏览器不支持 video 标签
        </video>

    </div>

</div>

<!-- 视频 & 广告控制逻辑 -->
<script src="${pageContext.request.contextPath}/assets/js/video-player.js"></script>

</body>
</html>
