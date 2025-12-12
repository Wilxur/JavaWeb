<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>广告管理平台 - 登录</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-container {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }
        .login-header h2 {
            color: #333;
            font-weight: 600;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            width: 100%;
            padding: 0.75rem;
            font-weight: 600;
        }
        .alert {
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <h2>广告管理平台</h2>
        <p class="text-muted">请登录您的账户</p>
    </div>

    <!-- 错误消息显示区域 -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>登录失败！</strong> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/auth" method="post">
        <!-- 用户名/邮箱输入 -->
        <div class="mb-3">
            <label for="username" class="form-label">用户名</label>
            <input type="text"
                   class="form-control"
                   id="username"
                   name="username"
                   placeholder="请输入用户名"
                   required
                   autofocus>
        </div>

        <!-- 密码输入 -->
        <div class="mb-3">
            <label for="password" class="form-label">密码</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"
                   placeholder="请输入密码"
                   required>
        </div>

        <!-- 记住我复选框 -->
        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
            <label class="form-check-label" for="rememberMe">记住我（7天）</label>
        </div>

        <!-- 提交按钮 -->
        <button type="submit" class="btn btn-primary">登录</button>
    </form>

    <%-- 在</form>标签后添加 --%>
    <hr class="my-3">
    <div class="text-center">
        <p class="text-muted">还没有账户？<a href="${pageContext.request.contextPath}/register" class="text-primary">立即注册</a></p>
    </div>

    <hr class="my-3">
    <div class="text-center text-muted">
        <small>广告管理平台 v1.0</small>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>