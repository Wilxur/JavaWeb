<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<div class="auth-page">
    <div class="auth-card">

        <div class="auth-header">
            <div class="auth-logo">VP</div>
            <h2 class="auth-title">创建账号</h2>
            <div class="auth-subtitle">加入 Video Platform</div>
        </div>

        <form class="form" action="${pageContext.request.contextPath}/register" method="post">

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

            <button class="btn primary" type="submit">注册</button>
        </form>

        <div class="auth-footer">
            已有账号？
            <a href="${pageContext.request.contextPath}/login">返回登录</a>
        </div>

    </div>
</div>

</body>
</html>
