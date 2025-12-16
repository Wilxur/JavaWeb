<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>NewsSite - 首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<div style="text-align:center; margin-top: 80px;">

    <h1>欢迎来到 NewsSite 新闻平台</h1>
    <p>本平台支持新闻发布、分类浏览、用户行为上报、广告投放对接等功能。</p>

    <br><br>

    <a href="${pageContext.request.contextPath}/news/list"
       style="padding:10px 20px; background:#2196F3; color:white; text-decoration:none; border-radius:5px;">
        进入新闻列表
    </a>

</div>
</body>
</html>