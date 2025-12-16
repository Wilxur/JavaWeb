<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>${video.title}</title>
</head>
<body>

<p>
    <a href="${pageContext.request.contextPath}/videos">← 返回视频列表</a>
</p>

<h2>${video.title}</h2>

<video
        id="mainVideo"
        width="800"
        controls
        preload="metadata"
        src="${pageContext.request.contextPath}/${video.videoUrl}">
    您的浏览器不支持 video 标签
</video>
<script src="${pageContext.request.contextPath}/assets/js/video-player.js"></script>

</body>
</html>
