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
    <title>购物网站 - 用户注册</title>
    <style>
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

        .register-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 450px;
            padding: 40px;
        }

        .register-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .register-header h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 10px;
        }

        .register-header p {
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

        .btn-register {
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
            margin-top: 10px;
        }

        .btn-register:hover {
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
        }

        .login-link {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
        }

        .login-link a {
            color: #667eea;
            text-decoration: none;
        }

        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="register-container">
    <div class="register-header">
        <h1>创建账户</h1>
        <p>注册新的购物网站账户</p>
    </div>

    <c:if test="${not empty requestScope.error}">
        <div class="error-message">
                ${requestScope.error}
        </div>
    </c:if>

    <form action="register" method="post" id="registerForm">
        <div class="form-group">
            <label for="username">用户名 *</label>
            <input type="text" id="username" name="username"
                   class="form-control" placeholder="请输入用户名"
                   required>
        </div>

        <div class="form-group">
            <label for="password">密码 *</label>
            <input type="password" id="password" name="password"
                   class="form-control" placeholder="请输入密码"
                   required>
        </div>

        <div class="form-group">
            <label for="confirmPassword">确认密码 *</label>
            <input type="password" id="confirmPassword" name="confirmPassword"
                   class="form-control" placeholder="请再次输入密码"
                   required>
        </div>

        <div class="form-group">
            <label for="email">邮箱</label>
            <input type="email" id="email" name="email"
                   class="form-control" placeholder="请输入邮箱">
        </div>

        <div class="form-group">
            <label for="phone">电话</label>
            <input type="tel" id="phone" name="phone"
                   class="form-control" placeholder="请输入电话号码">
        </div>

        <button type="submit" class="btn-register">注册</button>
    </form>

    <div class="login-link">
        已有账户？<a href="login">立即登录</a>
    </div>
</div>

<script>
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        var username = document.getElementById('username').value.trim();
        var password = document.getElementById('password').value.trim();
        var confirmPassword = document.getElementById('confirmPassword').value.trim();

        var errors = [];

        if (username.length < 3 || username.length > 20) {
            errors.push('用户名长度应在3-20个字符之间');
        }

        if (password.length < 6) {
            errors.push('密码长度至少6位');
        }

        if (password !== confirmPassword) {
            errors.push('两次输入的密码不一致');
        }

        if (errors.length > 0) {
            e.preventDefault();
            alert(errors.join('\n'));
            return false;
        }
    });
</script>
</body>
</html>