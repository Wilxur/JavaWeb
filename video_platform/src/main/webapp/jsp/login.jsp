<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<div class="auth-page">
    <div class="auth-card">

        <div class="auth-header">
            <div class="auth-logo">VP</div>
            <h2 class="auth-title">Video Platform</h2>
            <div class="auth-subtitle">Java Web 视频平台登录</div>
        </div>

        <form class="form" action="${pageContext.request.contextPath}/loginDo" method="post">

            <div class="field">
                <label class="label">用户名</label>
                <input class="input" type="text" name="username" required>
            </div>

            <div class="field">
                <label class="label">密码</label>
                <input class="input" type="password" name="password" required>
            </div>

            <div class="field">
                <label class="label">验证码</label>
                <div class="captcha">
                    <input class="input" type="text" name="captcha" required>
                    <img src="${pageContext.request.contextPath}/captcha"
                         onclick="this.src='${pageContext.request.contextPath}/captcha?'+Math.random()">
                </div>
            </div>

            <c:if test="${not empty param.error}">
                <div class="alert">${param.error}</div>
            </c:if>

            <button class="btn primary" type="submit">登录</button>
        </form>

        <div class="auth-footer">
            还没有账号？
            <a href="${pageContext.request.contextPath}/registerPage">立即注册</a>
        </div>

    </div>
</div>

</body>
</html>
