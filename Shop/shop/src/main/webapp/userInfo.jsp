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
    <title>购物网站 - 个人中心</title>
    <style>
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
            position: sticky;
            top: 0;
            z-index: 100;
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
            gap: 20px;
        }

        .welcome {
            color: #666;
        }

        .username {
            color: #667eea;
            font-weight: bold;
        }

        .nav-links {
            display: flex;
            gap: 20px;
        }

        .nav-link {
            color: #666;
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 5px;
            transition: all 0.3s;
        }

        .nav-link:hover {
            color: #667eea;
            background: #f0f0f0;
        }

        .nav-link.active {
            color: #667eea;
            background: #f0f0f0;
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

        .page-title {
            text-align: center;
            margin-bottom: 40px;
            color: #333;
        }

        .page-title h1 {
            font-size: 36px;
            margin-bottom: 10px;
        }

        .page-title p {
            color: #666;
            font-size: 16px;
        }

        .user-profile {
            display: grid;
            grid-template-columns: 300px 1fr;
            gap: 40px;
            margin-top: 40px;
        }

        .profile-sidebar {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            height: fit-content;
        }

        .profile-content {
            background: white;
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .avatar {
            text-align: center;
            margin-bottom: 30px;
        }

        .avatar-img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            color: white;
            margin: 0 auto 20px;
        }

        .avatar-img .initials {
            font-weight: bold;
        }

        .avatar-name {
            font-size: 20px;
            color: #333;
            margin-bottom: 5px;
        }

        .avatar-level {
            color: #667eea;
            font-size: 14px;
            font-weight: 500;
        }

        .sidebar-menu {
            list-style: none;
        }

        .sidebar-menu li {
            margin-bottom: 10px;
        }

        .sidebar-menu a {
            display: block;
            padding: 12px 20px;
            color: #666;
            text-decoration: none;
            border-radius: 8px;
            transition: all 0.3s;
        }

        .sidebar-menu a:hover,
        .sidebar-menu a.active {
            background: #667eea;
            color: white;
        }

        .sidebar-menu a i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }

        .section-title {
            font-size: 24px;
            color: #333;
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 30px;
            margin-bottom: 40px;
        }

        .info-card {
            background: #f9f9f9;
            padding: 25px;
            border-radius: 10px;
            border-left: 4px solid #667eea;
        }

        .info-label {
            font-size: 14px;
            color: #666;
            margin-bottom: 8px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .info-value {
            font-size: 18px;
            color: #333;
            font-weight: 500;
        }

        .info-value.empty {
            color: #999;
            font-style: italic;
        }

        .info-actions {
            display: flex;
            gap: 15px;
            margin-top: 15px;
        }

        .btn-edit, .btn-change {
            background: #667eea;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            transition: background 0.3s;
        }

        .btn-edit:hover, .btn-change:hover {
            background: #764ba2;
        }

        .btn-change {
            background: #f0f0f0;
            color: #666;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-top: 40px;
        }

        .stat-item {
            text-align: center;
            padding: 20px;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            border-radius: 10px;
        }

        .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 10px;
        }

        .stat-label {
            color: #666;
            font-size: 14px;
        }

        @media (max-width: 992px) {
            .user-profile {
                grid-template-columns: 1fr;
            }

            .info-grid {
                grid-template-columns: 1fr;
            }

            .stats-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 768px) {
            .header-content {
                flex-direction: column;
                gap: 15px;
            }

            .nav-links {
                flex-wrap: wrap;
                justify-content: center;
            }

            .user-info {
                flex-direction: column;
                gap: 10px;
            }

            .stats-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header-content">
            <a href="index.jsp" class="logo">购物网站</a>

            <div class="nav-links">
                <a href="index.jsp" class="nav-link">商品分类</a>
                <a href="userInfo.jsp" class="nav-link active">个人中心</a>
                <a href="cart" class="nav-link">购物车</a>  <!-- ✅ 已修复：cart -->
                <a href="order" class="nav-link">我的订单</a>  <!-- ✅ 已修复：order -->
                <a href="#" class="nav-link">收藏夹</a>
            </div>

            <div class="user-info">
                <span class="welcome">欢迎，<span class="username">${sessionScope.user.username}</span></span>
                <a href="logout" class="btn-logout">退出登录</a>
            </div>
        </div>
    </div>
</header>

<main class="main-content">
    <div class="container">
        <div class="page-title">
            <h1>个人中心</h1>
            <p>管理您的账户信息、订单和偏好设置</p>
        </div>

        <div class="user-profile">
            <!-- 侧边栏 -->
            <div class="profile-sidebar">
                <div class="avatar">
                    <div class="avatar-img">
                        <span class="initials">${sessionScope.user.username.charAt(0)}</span>
                    </div>
                    <h3 class="avatar-name">${sessionScope.user.username}</h3>
                    <div class="avatar-level">VIP会员</div>
                </div>

                <ul class="sidebar-menu">
                    <li><a href="#" class="active">📊 账户概览</a></li>
                    <li><a href="#">📝 个人信息</a></li>
                    <li><a href="#">🔐 安全设置</a></li>
                    <li><a href="#">📦 我的订单</a></li>
                    <li><a href="#">❤️ 我的收藏</a></li>
                    <li><a href="#">💳 我的钱包</a></li>
                    <li><a href="#">⚙️ 账户设置</a></li>
                </ul>
            </div>

            <!-- 主要内容 -->
            <div class="profile-content">
                <h2 class="section-title">账户信息</h2>

                <div class="info-grid">
                    <!-- 基本信息 -->
                    <div class="info-card">
                        <div class="info-label">用户名</div>
                        <div class="info-value">${sessionScope.user.username}</div>
                        <div class="info-actions">
                            <a href="#" class="btn-change">更改</a>
                        </div>
                    </div>

                    <!-- 邮箱 -->
                    <div class="info-card">
                        <div class="info-label">电子邮箱</div>
                        <c:choose>
                            <c:when test="${not empty sessionScope.user.email}">
                                <div class="info-value">${sessionScope.user.email}</div>
                            </c:when>
                            <c:otherwise>
                                <div class="info-value empty">未设置邮箱</div>
                            </c:otherwise>
                        </c:choose>
                        <div class="info-actions">
                            <a href="#" class="btn-change">更改</a>
                        </div>
                    </div>

                    <!-- 电话 -->
                    <div class="info-card">
                        <div class="info-label">手机号码</div>
                        <c:choose>
                            <c:when test="${not empty sessionScope.user.phone}">
                                <div class="info-value">${sessionScope.user.phone}</div>
                            </c:when>
                            <c:otherwise>
                                <div class="info-value empty">未设置手机号</div>
                            </c:otherwise>
                        </c:choose>
                        <div class="info-actions">
                            <a href="#" class="btn-change">更改</a>
                        </div>
                    </div>

                    <!-- 账户状态 -->
                    <div class="info-card">
                        <div class="info-label">账户状态</div>
                        <div class="info-value">正常</div>
                        <div class="info-actions">
                            <a href="#" class="btn-edit">升级VIP</a>
                        </div>
                    </div>

                    <!-- 注册时间 -->
                    <div class="info-card">
                        <div class="info-label">注册时间</div>
                        <div class="info-value">2024-01-15</div>
                        <div class="info-actions">
                            <a href="#" class="btn-change">查看详情</a>
                        </div>
                    </div>

                    <!-- 最后登录 -->
                    <div class="info-card">
                        <div class="info-label">最后登录</div>
                        <div class="info-value">今天 14:30</div>
                        <div class="info-actions">
                            <a href="#" class="btn-change">登录记录</a>
                        </div>
                    </div>
                </div>

                <!-- 统计数据 -->
                <h2 class="section-title">我的数据统计</h2>

                <div class="stats-grid">
                    <div class="stat-item">
                        <div class="stat-number">12</div>
                        <div class="stat-label">我的订单</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">8</div>
                        <div class="stat-label">购物车</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">24</div>
                        <div class="stat-label">我的收藏</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">3</div>
                        <div class="stat-label">优惠券</div>
                    </div>
                </div>

                <!-- 快速操作 -->
                <h2 class="section-title">快速操作</h2>

                <div class="info-actions" style="margin-top: 20px;">
                    <a href="#" class="btn-edit">修改密码</a>
                    <a href="#" class="btn-edit">编辑资料</a>
                    <a href="#" class="btn-edit">消息通知</a>
                    <a href="#" class="btn-edit">收货地址</a>
                    <a href="#" class="btn-edit">帮助中心</a>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    // 生成用户头像首字母
    function generateAvatarInitials() {
        const username = '${sessionScope.user.username}';
        if (username) {
            return username.charAt(0).toUpperCase();
        }
        return 'U';
    }

    document.addEventListener('DOMContentLoaded', function() {
        const avatarInitial = document.querySelector('.initials');
        if (avatarInitial) {
            avatarInitial.textContent = generateAvatarInitials();
        }

        // 侧边栏菜单点击效果
        const menuItems = document.querySelectorAll('.sidebar-menu a');
        menuItems.forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                menuItems.forEach(i => i.classList.remove('active'));
                this.classList.add('active');
            });
        });
    });
</script>
</body>
</html>