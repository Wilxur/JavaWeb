<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>

    <style>
        /* ===== 页面基础 ===== */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #2563eb, #60a5fa);
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI",
            "PingFang SC", "Microsoft YaHei", Arial, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* ===== 登录卡片 ===== */
        .login-card {
            width: 380px;
            background: #ffffff;
            border-radius: 16px;
            padding: 36px 32px 30px;
            box-shadow: 0 20px 45px rgba(0, 0, 0, 0.18);
        }

        .login-card h2 {
            text-align: center;
            margin-bottom: 28px;
            color: #1f2937;
            letter-spacing: 1px;
        }

        /* ===== 表单区域 ===== */
        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #374151;
        }

        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            font-size: 14px;
        }

        .form-group input:focus {
            outline: none;
            border-color: #2563eb;
            box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.15);
        }

        /* ===== 按钮 ===== */
        .login-btn {
            width: 100%;
            margin-top: 10px;
            padding: 12px 0;
            background: linear-gradient(90deg, #2563eb, #3b82f6);
            border: none;
            border-radius: 10px;
            color: white;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            letter-spacing: 1px;
        }

        .login-btn:hover {
            background: linear-gradient(90deg, #1d4ed8, #2563eb);
        }

        /* ===== 错误提示 ===== */
        .error-msg {
            margin-top: 14px;
            text-align: center;
            color: #dc2626;
            font-size: 14px;
        }

        /* ===== 底部注册链接 ===== */
        .register-link {
            margin-top: 26px;
            text-align: center;
            font-size: 14px;
            color: #6b7280;
        }

        .register-link a {
            color: #2563eb;
            text-decoration: none;
            font-weight: 500;
        }

        .register-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="login-card">

    <h2>用户登录</h2>

    <form method="post" action="${pageContext.request.contextPath}/login">

        <div class="form-group">
            <label>用户名</label>
            <input type="text" name="username" required />
        </div>

        <div class="form-group">
            <label>密码</label>
            <input type="password" name="password" required />
        </div>

        <button class="login-btn" type="submit">
            登录
        </button>

    </form>

    <!-- 登录失败提示 -->
    <c:if test="${not empty error}">
        <div class="error-msg">
                ${error}
        </div>
    </c:if>

    <!-- 注册入口 -->
    <div class="register-link">
        还没有账号？
        <a href="${pageContext.request.contextPath}/register">
            去注册
        </a>
    </div>

</div>

</body>
</html>