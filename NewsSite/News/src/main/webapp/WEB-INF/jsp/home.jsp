<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>新闻首页</title>
</head>
<body>

<!-- ===== 登录状态区域（关键） ===== -->
<div>
    <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
            <span>
                欢迎你，
                <strong>${sessionScope.loginUser.username}</strong>
            </span>
            |
            <!-- ⚠️ 必须这样写，才能真的触发 LogoutServlet -->
            <a href="${pageContext.request.contextPath}/logout">
                退出登录
            </a>
        </c:when>

        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login">
                去登录
            </a>
        </c:otherwise>
    </c:choose>
</div>

<hr/>

<!-- ===== 新闻列表 ===== -->
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