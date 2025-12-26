<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>新闻首页</title>
</head>
<body>

<!-- ===== 登录状态区域 ===== -->
<div>
    <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
            <span>
                欢迎你，
                <strong>${sessionScope.loginUser.username}</strong>
            </span>
            |
            <!-- 发布新闻入口（关键新增） -->
            <a href="${pageContext.request.contextPath}/news/add">
                发布新闻
            </a>
            |
            <!-- 退出登录 -->
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

        <li style="margin-bottom:12px;">
            <div>
                <a href="${pageContext.request.contextPath}/detail?id=${news.id}">
                    【${news.category}】${news.title}
                </a>
            </div>

            <!-- 编辑入口：仅登录用户可见 -->
            <c:if test="${not empty sessionScope.loginUser}">
                <div style="font-size:12px; color:#666; margin-top:4px;">
                    <a href="${pageContext.request.contextPath}/news/edit?id=${news.id}">
                        编辑
                    </a>
                </div>
            </c:if>
        </li>

    </c:forEach>
</ul>

</body>
</html>