<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>新闻首页</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<h2>新闻列表</h2>

<ul>
    <c:forEach var="news" items="${newsList}">
        <li>
            <a href="${pageContext.request.contextPath}/detail?id=${news.id}">
                【${news.category}】${news.title}
            </a>
        </li>
    </c:forEach>
</ul>

</body>
</html>