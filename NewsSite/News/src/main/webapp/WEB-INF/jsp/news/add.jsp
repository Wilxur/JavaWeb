<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>发布新闻</title>
</head>
<body>

<h2>发布新闻</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/news/add">
    <p>
        标题：<br/>
        <input type="text" name="title" style="width: 520px;" required />
    </p>

    <p>
        分类：<br/>
        <select name="categoryId" required>
            <option value="">请选择分类</option>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}">${c.name}</option>
            </c:forEach>
        </select>
    </p>

    <p>
        内容：<br/>
        <textarea name="content" rows="10" cols="80" required></textarea>
    </p>

    <button type="submit">发布</button>
</form>

<p>
    <a href="${pageContext.request.contextPath}/home">← 返回首页</a>
</p>

</body>
</html>