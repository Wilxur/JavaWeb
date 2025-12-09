<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>新闻列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<h2>新闻列表</h2>

<!-- 分类筛选 -->
<div>
    <a href="${pageContext.request.contextPath}/news/list">全部</a>
    <c:forEach var="c" items="${categories}">
        |
        <a href="${pageContext.request.contextPath}/news/list?categoryId=${c.categoryId}">
                ${c.categoryName}
        </a>
    </c:forEach>
</div>

<br>

<!-- 发布新闻按钮 -->
<a href="${pageContext.request.contextPath}/news/add">发布新闻</a>

<hr>

<!-- 新闻列表 -->
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>标题</th>
        <th>分类</th>
        <th>作者</th>
        <th>发布时间</th>
        <th>操作</th>
    </tr>

    <c:forEach var="n" items="${newsList}">
        <tr>
            <td>${n.newsId}</td>

            <!-- 点击标题跳转到详情 -->
            <td>
                <a href="${pageContext.request.contextPath}/news/detail?id=${n.newsId}">
                        ${n.title}
                </a>
            </td>

            <td>${n.categoryName}</td>
            <td>${n.author}</td>
            <td>${n.publishedAt}</td>

            <td>
                <a href="${pageContext.request.contextPath}/news/edit?id=${n.newsId}">编辑</a>
                |
                <a href="${pageContext.request.contextPath}/news/delete?id=${n.newsId}"
                   onclick="return confirm('确定删除?');">
                    删除
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>