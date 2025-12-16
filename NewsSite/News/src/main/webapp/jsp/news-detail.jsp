<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${news.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<input type="hidden" id="newsId" value="${news.newsId}">
<input type="hidden" id="categoryId" value="${news.categoryId}">
<script src="${pageContext.request.contextPath}/js/behavior.js"></script>

<body>
<h2>${news.title}</h2>

<p>
    分类：${news.categoryName} |
    作者：${news.author} |
    发布时间：${news.publishedAt} |
    浏览量：${news.viewCount}
</p>

<hr>

<p>${news.content}</p>

<hr>

<a href="${pageContext.request.contextPath}/news/list">返回列表</a>
</body>
</html>