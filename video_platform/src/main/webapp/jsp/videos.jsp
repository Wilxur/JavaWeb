<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>视频列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<div class="container">

    <div class="topbar">
        <div class="brand">Video Platform <span class="badge">JavaWeb</span></div>

        <div class="nav">
            <!-- 已登录 -->
            <c:if test="${not empty sessionScope.user}">
                <span class="welcome">
                    欢迎，${sessionScope.user.username}
                </span>
                <a class="btn" href="${pageContext.request.contextPath}/upload">上传视频</a>
                <a class="btn danger" href="${pageContext.request.contextPath}/logout">退出</a>
            </c:if>

            <!-- 未登录 -->
            <c:if test="${empty sessionScope.user}">
                <a class="btn primary" href="${pageContext.request.contextPath}/login">登录</a>
                <a class="btn" href="${pageContext.request.contextPath}/registerPage">注册</a>
            </c:if>
        </div>
    </div>

    <div class="card">
        <form class="toolbar" method="get" action="${pageContext.request.contextPath}/videos">
            <div class="row">
                <span class="label">分类筛选：</span>
                <select name="categoryId" onchange="this.form.submit()">
                    <option value="">全部</option>
                    <c:forEach items="${categories}" var="c">
                        <option value="${c.id}"
                                <c:if test="${selectedCategoryId == c.id}">selected</c:if>>
                                ${c.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </form>

        <c:if test="${empty videos}">
            <p class="helper">暂无视频</p>
        </c:if>

        <ul class="list">
            <c:forEach items="${videos}" var="v">
                <li class="item">
                    <div class="meta">
                        <b>${v.title}</b>
                        <span class="tag">${v.categoryName}</span>
                    </div>
                    <a class="btn primary"
                       href="${pageContext.request.contextPath}/video?id=${v.id}">
                        播放
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

</div>

</body>
</html>
