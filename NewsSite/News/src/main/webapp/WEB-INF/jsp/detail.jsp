<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>${news.title}</title>

    <!-- 给广告平台的分类说明符 -->
    <meta name="ad-category" content="${news.category}">

    <link rel="stylesheet" href="${pageContext.request.contextPath}css/detail.css">
</head>
<body>

<h2>${news.title}</h2>

<p>分类：${news.category}</p>

<div>
    ${news.content}
</div>

<div class="test-css">
    CSS 正在生效
</div>

<hr/>

<!-- 广告展示容器 -->
<div id="ad-container"></div>

<!-- 广告平台 SDK -->
<script src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>

<p>
    <a href="${pageContext.request.contextPath}/home">返回首页</a>
</p>

</body>
</html>