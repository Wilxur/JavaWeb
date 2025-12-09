<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>发布新闻</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<h2>发布新闻</h2>

<form method="post" action="${pageContext.request.contextPath}/news/add">

    <label>标题：</label><br>
    <input type="text" name="title" required><br><br>

    <label>作者：</label><br>
    <input type="text" name="author" value="匿名"><br><br>

    <label>分类：</label><br>
    <select name="categoryId" required>
        <c:forEach var="c" items="${categories}">
            <option value="${c.categoryId}">${c.categoryName}</option>
        </c:forEach>
    </select><br><br>

    <label>内容：</label><br>
    <textarea name="content" rows="10" cols="60" required></textarea><br><br>

    <button type="submit">提交</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/news/list">返回列表</a>

</body>
</html>