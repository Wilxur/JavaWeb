<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>编辑新闻</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<h2>编辑新闻</h2>

<form method="post" action="${pageContext.request.contextPath}/news/edit">

    <input type="hidden" name="id" value="${news.newsId}">

    <label>标题：</label><br>
    <input type="text" name="title" value="${news.title}" required><br><br>

    <label>作者：</label><br>
    <input type="text" name="author" value="${news.author}"><br><br>

    <label>分类：</label><br>
    <select name="categoryId">
        <c:forEach var="c" items="${categories}">
            <option value="${c.categoryId}"
                    <c:if test="${c.categoryId == news.categoryId}">selected</c:if>>
                    ${c.categoryName}
            </option>
        </c:forEach>
    </select><br><br>

    <label>内容：</label><br>
    <textarea name="content" rows="10" cols="60">${news.content}</textarea><br><br>

    <button type="submit">保存</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/news/detail?id=${news.newsId}">返回详情</a>

</body>
</html>