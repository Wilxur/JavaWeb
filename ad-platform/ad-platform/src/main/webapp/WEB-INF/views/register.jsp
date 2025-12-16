<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>广告管理平台 - 注册</title>
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
        .register-container {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 450px;
        }
        .register-header {
            text-align: center;
            margin-bottom: 2rem;
        }
        .register-header h2 {
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
<div class="register-container">
    <div class="register-header">
        <h2>创建账户</h2>
        <p class="text-muted">欢迎加入广告管理平台</p>
    </div>

    <!-- 错误/成功消息显示区域 -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>注册失败！</strong> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>注册成功！</strong> ${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <!-- 用户名输入 -->
        <div class="mb-3">
            <label for="username" class="form-label">用户名</label>
            <input type="text"
                   class="form-control"
                   id="username"
                   name="username"
                   placeholder="请输入用户名（唯一）"
                   required
                   autofocus>
            <div class="form-text">用户名长度3-20位，支持字母、数字、下划线</div>
        </div>

        <!-- 邮箱输入 -->
        <div class="mb-3">
            <label for="email" class="form-label">邮箱地址</label>
            <input type="email"
                   class="form-control"
                   id="email"
                   name="email"
                   placeholder="请输入邮箱"
                   required>
        </div>

        <!-- 密码输入 -->
        <div class="mb-3">
            <label for="password" class="form-label">密码</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"
                   placeholder="请输入密码（至少6位）"
                   required
                   minlength="6">
        </div>

        <!-- 确认密码输入 -->
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">确认密码</label>
            <input type="password"
                   class="form-control"
                   id="confirmPassword"
                   name="confirmPassword"
                   placeholder="请再次输入密码"
                   required
                   minlength="6">
        </div>

        <!-- 提交按钮 -->
        <button type="submit" class="btn btn-primary">立即注册</button>
    </form>

    <hr class="my-3">
    <div class="text-center">
        <p class="text-muted">已有账户？<a href="${pageContext.request.contextPath}/login" class="text-primary">立即登录</a></p>
    </div>
    <div class="text-center text-muted">
        <small>广告管理平台 v1.0</small>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>