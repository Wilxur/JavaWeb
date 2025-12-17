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
    <title>购物网站 - 商品分类</title>
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

        .categories-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-top: 40px;
        }

        .category-card {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s, box-shadow 0.3s;
            cursor: pointer;
        }

        .category-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
        }

        .category-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .category-content {
            padding: 25px;
        }

        .category-title {
            font-size: 20px;
            color: #333;
            margin-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .category-title h3 {
            font-size: 24px;
        }

        .category-count {
            background: #667eea;
            color: white;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 14px;
        }

        .category-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 20px;
        }

        .category-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .btn-browse {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            transition: transform 0.3s;
        }

        .btn-browse:hover {
            transform: scale(1.05);
        }

        .hot-label {
            color: #ff6b6b;
            font-size: 14px;
            font-weight: bold;
        }

        /* 每个类别的独特颜色 */
        .category-card:nth-child(1) .category-count {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .category-card:nth-child(2) .category-count {
            background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
        }
        .category-card:nth-child(3) .category-count {
            background: linear-gradient(135deg, #a1c4fd 0%, #c2e9fb 100%);
        }
        .category-card:nth-child(4) .category-count {
            background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
        }
        .category-card:nth-child(5) .category-count {
            background: linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%);
        }
        .category-card:nth-child(6) .category-count {
            background: linear-gradient(135deg, #d4fc79 0%, #96e6a1 100%);
        }

        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 20px;
            margin-top: 40px;
            margin-bottom: 40px;
        }

        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
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

            .categories-grid {
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
                <a href="index.jsp" class="nav-link active">商品分类</a>
                <a href="userInfo.jsp" class="nav-link">个人中心</a>
                <a href="#" class="nav-link">购物车</a>
                <a href="#" class="nav-link">我的订单</a>
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
            <h1>发现精彩商品</h1>
            <p>探索六大热门商品类别，满足您的购物需求</p>
        </div>

        <div class="stats">
            <div class="stat-card">
                <div class="stat-number">1,234+</div>
                <div class="stat-label">在售商品</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">500+</div>
                <div class="stat-label">品牌商家</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">98%</div>
                <div class="stat-label">用户好评</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">24h</div>
                <div class="stat-label">闪电发货</div>
            </div>
        </div>

        <div class="categories-grid">
            <!-- 电子产品 -->
            <div class="category-card" onclick="window.location.href='products.jsp?category=electronics'">
                <img src="https://images.unsplash.com/photo-1498049794561-7780e7231661?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80"
                     alt="电子产品" class="category-image">
                <div class="category-content">
                    <div class="category-title">
                        <h3>电子产品</h3>
                        <span class="category-count">245件商品</span>
                    </div>
                    <p class="category-description">
                        手机、电脑、平板、耳机、智能手表等最新科技产品，享受科技带来的便捷生活。
                    </p>
                    <div class="category-actions">
                        <span class="hot-label">🔥 热门推荐</span>
                        <a href="products.jsp?category=electronics" class="btn-browse">立即选购</a>
                    </div>
                </div>
            </div>

            <!-- 服装鞋帽 -->
            <div class="category-card" onclick="window.location.href='products.jsp?category=clothing'">
                <img src="https://images.unsplash.com/photo-1445205170230-053b83016050?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80"
                     alt="服装鞋帽" class="category-image">
                <div class="category-content">
                    <div class="category-title">
                        <h3>服装鞋帽</h3>
                        <span class="category-count">156件商品</span>
                    </div>
                    <p class="category-description">
                        时尚服饰、潮流鞋帽、配饰等，打造个性穿搭风格，展现独特魅力。
                    </p>
                    <div class="category-actions">
                        <span class="hot-label">🆕 新品上市</span>
                        <a href="products.jsp?category=clothing" class="btn-browse">立即选购</a>
                    </div>
                </div>
            </div>

            <!-- 食品饮料 -->
            <div class="category-card" onclick="window.location.href='products.jsp?category=food'">
                <img src="https://images.unsplash.com/photo-1490818387583-1baba5e638af?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80"
                     alt="食品饮料" class="category-image">
                <div class="category-content">
                    <div class="category-title">
                        <h3>食品饮料</h3>
                        <span class="category-count">389件商品</span>
                    </div>
                    <p class="category-description">
                        休闲零食、地方特产、进口食品、健康饮品，满足您的味蕾享受。
                    </p>
                    <div class="category-actions">
                        <span class="hot-label">💯 好评如潮</span>
                        <a href="products.jsp?category=food" class="btn-browse">立即选购</a>
                    </div>
                </div>
            </div>

            <!-- 美妆护肤 -->
            <div class="category-card" onclick="window.location.href='products.jsp?category=beauty'">
                <img src="https://images.unsplash.com/photo-1596462502278-27bfdc403348?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80"
                     alt="美妆护肤" class="category-image">
                <div class="category-content">
                    <div class="category-title">
                        <h3>美妆护肤</h3>
                        <span class="category-count">278件商品</span>
                    </div>
                    <p class="category-description">
                        护肤品、化妆品、香水、美发美甲产品，呵护肌肤，展现美丽自信。
                    </p>
                    <div class="category-actions">
                        <span class="hot-label">💄 热销爆款</span>
                        <a href="products.jsp?category=beauty" class="btn-browse">立即选购</a>
                    </div>
                </div>
            </div>

            <!-- 家居用品 -->
            <div class="category-card" onclick="window.location.href='products.jsp?category=home'">
                <img src="https://images.unsplash.com/photo-1556228453-efd6c1ff04f6?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80"
                     alt="家居用品" class="category-image">
                <div class="category-content">
                    <div class="category-title">
                        <h3>家居用品</h3>
                        <span class="category-count">312件商品</span>
                    </div>
                    <p class="category-description">
                        家具家纺、厨房用具、收纳整理、装饰摆件，打造温馨舒适的家居环境。
                    </p>
                    <div class="category-actions">
                        <span class="hot-label">🏠 品质生活</span>
                        <a href="products.jsp?category=home" class="btn-browse">立即选购</a>
                    </div>
                </div>
            </div>

            <!-- 运动户外 -->
            <div class="category-card" onclick="window.location.href='products.jsp?category=sports'">
                <img src="https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80"
                     alt="运动户外" class="category-image">
                <div class="category-content">
                    <div class="category-title">
                        <h3>运动户外</h3>
                        <span class="category-count">187件商品</span>
                    </div>
                    <p class="category-description">
                        运动装备、户外用品、健身器材、体育用品，支持您的健康活力生活方式。
                    </p>
                    <div class="category-actions">
                        <span class="hot-label">🏃‍♂️ 活力无限</span>
                        <a href="products.jsp?category=sports" class="btn-browse">立即选购</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    // 添加分类卡片悬停效果
    document.querySelectorAll('.category-card').forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-10px)';
            this.style.boxShadow = '0 15px 30px rgba(0, 0, 0, 0.15)';
        });

        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
            this.style.boxShadow = '0 5px 20px rgba(0, 0, 0, 0.1)';
        });
    });
</script>
</body>
</html>