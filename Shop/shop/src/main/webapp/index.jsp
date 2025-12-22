<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.isLoggedIn or not sessionScope.isLoggedIn}">
    <c:redirect url="/login" />
</c:if>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>购物网站 - 首页</title>
    <style>
        /* 样式保持不变 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: #f5f5f5;
        }

        .header {
            background: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 15px 0;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #667eea;
            text-decoration: none;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .welcome {
            color: #666;
        }

        .username {
            color: #667eea;
            font-weight: bold;
        }

        .btn-logout {
            background: #e74c3c;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-logout:hover {
            background: #c0392b;
        }

        .main-content {
            padding: 40px 0;
        }

        .dashboard {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
        }

        .dashboard h1 {
            color: #333;
            margin-bottom: 20px;
        }

        .user-details {
            background: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
        }

        .user-details h2 {
            color: #667eea;
            margin-bottom: 15px;
        }

        .detail-item {
            display: flex;
            margin-bottom: 10px;
        }

        .detail-label {
            width: 100px;
            color: #666;
            font-weight: 500;
        }

        .detail-value {
            color: #333;
        }

        .actions {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }

        .action-btn {
            background: #667eea;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            transition: background 0.3s;
        }

        .action-btn:hover {
            background: #764ba2;
        }
    </style>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header-content">
            <a href="index.jsp" class="logo">购物网站</a>
            <div class="user-info">
                <span class="welcome">欢迎，<span class="username">${sessionScope.user.username}</span></span>
                <a href="logout" class="btn-logout">退出登录</a>
            </div>
        </div>
    </div>
</header>

<main class="main-content">
    <div class="container">
        <div class="dashboard">
            <h1>用户控制面板</h1>

            <div class="user-details">
                <h2>账户信息</h2>
                <div class="detail-item">
                    <span class="detail-label">用户名：</span>
                    <span class="detail-value">${sessionScope.user.username}</span>
                </div>
                <c:if test="${not empty sessionScope.user.email}">
                    <div class="detail-item">
                        <span class="detail-label">邮箱：</span>
                        <span class="detail-value">${sessionScope.user.email}</span>
                    </div>
                </c:if>
                <c:if test="${not empty sessionScope.user.phone}">
                    <div class="detail-item">
                        <span class="detail-label">电话：</span>
                        <span class="detail-value">${sessionScope.user.phone}</span>
                    </div>
                </c:if>
            </div>

            <div class="actions">
                <a href="#" class="action-btn">浏览商品</a>
                <a href="#" class="action-btn">我的订单</a>
                <a href="#" class="action-btn">购物车</a>
                <a href="#" class="action-btn">个人信息</a>
                <a href="#" class="action-btn">修改密码</a>
            </div>
        </div>
    </div>
</main>
</body>
</html>