<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty sessionScope.isLoggedIn and sessionScope.isLoggedIn}">
    <c:redirect url="/index.jsp" />
</c:if>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>购物网站 - 用户登录</title>
    <style>
        /* 样式与之前相同，保持不变 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            padding: 40px;
        }

        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .login-header h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 10px;
        }

        .login-header p {
            color: #666;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: 500;
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        .form-control:focus {
            outline: none;
            border-color: #667eea;
        }

        .captcha-group {
            display: flex;
            gap: 10px;
            align-items: flex-end;
        }

        .captcha-input {
            flex: 1;
        }

        .captcha-image {
            height: 46px;
            border-radius: 8px;
            border: 2px solid #e1e1e1;
            cursor: pointer;
            transition: opacity 0.3s;
        }

        .captcha-image:hover {
            opacity: 0.8;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 20px;
        }

        .remember-me input[type="checkbox"] {
            width: 16px;
            height: 16px;
        }

        .btn-login {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
        }

        .error-message {
            background: #fee;
            color: #e74c3c;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #e74c3c;
            display: none;
        }

        .error-message.show {
            display: block;
        }

        .footer-links {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
        }

        .footer-links a {
            color: #667eea;
            text-decoration: none;
            margin: 0 10px;
        }

        .footer-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <h1>欢迎回来</h1>
        <p>登录您的购物网站账户</p>
    </div>

    <c:if test="${not empty requestScope.error}">
        <div class="error-message show" id="errorMessage">
                ${requestScope.error}
        </div>
    </c:if>
    <c:if test="${empty requestScope.error}">
        <div class="error-message" id="errorMessage"></div>
    </c:if>

    <form action="login" method="post" id="loginForm">
        <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username"
                   class="form-control" placeholder="请输入用户名"
                   value="${requestScope.savedUsername}" required>
        </div>

        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" id="password" name="password"
                   class="form-control" placeholder="请输入密码" required>
        </div>

        <div class="form-group">
            <label for="captcha">验证码</label>
            <div class="captcha-group">
                <input type="text" id="captcha" name="captcha"
                       class="form-control captcha-input"
                       placeholder="请输入验证码" maxlength="4" required>
                <img src="captcha" alt="验证码" class="captcha-image"
                     onclick="refreshCaptcha()" id="captchaImage"
                     title="点击刷新验证码">
            </div>
        </div>

        <div class="remember-me">
            <input type="checkbox" id="remember" name="remember"
                   <c:if test="${not empty requestScope.savedUsername}">checked</c:if>>
            <label for="remember">记住我</label>
        </div>

        <button type="submit" class="btn-login">登录</button>
    </form>

    <div class="footer-links">
        <a href="javascript:void(0)">忘记密码？</a>
        <a href="javascript:void(0)">注册账户</a>
    </div>
</div>

<script>
    function refreshCaptcha() {
        var captchaImage = document.getElementById('captchaImage');
        captchaImage.src = 'captcha?t=' + new Date().getTime();
    }

    // 表单验证
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        var username = document.getElementById('username').value.trim();
        var password = document.getElementById('password').value.trim();
        var captcha = document.getElementById('captcha').value.trim();
        var errorMessage = document.getElementById('errorMessage');

        errorMessage.classList.remove('show');
        errorMessage.textContent = '';

        var errors = [];

        if (!username) errors.push('用户名不能为空');
        if (!password) errors.push('密码不能为空');
        if (!captcha) errors.push('验证码不能为空');

        if (errors.length > 0) {
            e.preventDefault();
            errorMessage.textContent = errors.join('，');
            errorMessage.classList.add('show');
            return false;
        }

        if (!/^[a-zA-Z0-9]+$/.test(username)) {
            e.preventDefault();
            errorMessage.textContent = '用户名只能包含字母和数字';
            errorMessage.classList.add('show');
            return false;
        }

        if (password.length < 6) {
            e.preventDefault();
            errorMessage.textContent = '密码长度至少6位';
            errorMessage.classList.add('show');
            return false;
        }

        if (!/^[a-zA-Z0-9]{4}$/.test(captcha)) {
            e.preventDefault();
            errorMessage.textContent = '验证码必须为4位字母或数字';
            errorMessage.classList.add('show');
            return false;
        }
    });

    window.onload = function() {
        var captchaInput = document.getElementById('captcha');
        if (captchaInput.value === '') {
            refreshCaptcha();
        }
    };
</script>
</body>
</html>