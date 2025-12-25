<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 1. 登录检查（使用 JSTL 替代 Java 代码）--%>
<c:if test="${empty sessionScope.isLoggedIn or not sessionScope.isLoggedIn}">
    <c:redirect url="/login" />
</c:if>

<%-- 2. 获取分类参数和名称映射（使用 JSTL 替代 Java 代码）--%>
<c:set var="category" value="${empty param.category ? 'electronics' : param.category}" />

<c:choose>
    <c:when test="${category == 'electronics'}"><c:set var="categoryName" value="电子产品" /></c:when>
    <c:when test="${category == 'clothing'}"><c:set var="categoryName" value="服装鞋帽" /></c:when>
    <c:when test="${category == 'food'}"><c:set var="categoryName" value="食品饮料" /></c:when>
    <c:when test="${category == 'beauty'}"><c:set var="categoryName" value="美妆护肤" /></c:when>
    <c:when test="${category == 'home'}"><c:set var="categoryName" value="家居用品" /></c:when>
    <c:when test="${category == 'sports'}"><c:set var="categoryName" value="运动户外" /></c:when>
    <c:otherwise><c:set var="categoryName" value="未知分类" /></c:otherwise>
</c:choose>

<%-- 3. 获取商品数据（使用 JSTL 替代 Java 代码）--%>
<jsp:useBean id="productDAO" class="com.example.shop.ProductDAO" scope="page" />
<c:set var="products" value="${productDAO.getProductsByCategory(category)}" />

<%-- 4. 计算统计数据（使用 JSTL 替代 Java 代码）--%>
<c:set var="totalSales" value="0" />
<c:set var="totalRating" value="0" />
<c:forEach var="product" items="${products}">
    <c:set var="totalSales" value="${totalSales + product.sales}" />
    <c:set var="totalRating" value="${totalRating + product.rating}" />
</c:forEach>
<c:set var="averageRating" value="${not empty products ? totalRating / products.size() : 0}" />

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${categoryName} - 购物网站</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Microsoft YaHei', Arial, sans-serif; background: #f5f5f5; }
        .header { background: white; box-shadow: 0 2px 10px rgba(0,0,0,0.1); padding: 15px 0; position: sticky; top: 0; z-index: 100; }
        .container { max-width: 1400px; margin: 0 auto; padding: 0 20px; }
        .header-content { display: flex; justify-content: space-between; align-items: center; }
        .logo { font-size: 24px; font-weight: bold; color: #667eea; text-decoration: none; }
        .user-info { display: flex; align-items: center; gap: 20px; }
        .welcome { color: #666; }
        .username { color: #667eea; font-weight: bold; }
        .nav-links { display: flex; gap: 20px; }
        .nav-link { color: #666; text-decoration: none; padding: 5px 10px; border-radius: 5px; transition: all 0.3s; }
        .nav-link:hover { color: #667eea; background: #f0f0f0; }
        .nav-link.active { color: #667eea; background: #f0f0f0; }
        .btn-logout { background: #e74c3c; color: white; border: none; padding: 8px 16px; border-radius: 5px; cursor: pointer; text-decoration: none; font-size: 14px; }
        .btn-logout:hover { background: #c0392b; }
        .main-content { padding: 40px 0; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 40px; padding-bottom: 20px; border-bottom: 2px solid #eee; }
        .page-title h1 { font-size: 32px; color: #333; margin-bottom: 10px; }
        .page-title p { color: #666; }
        .category-filter { display: flex; gap: 10px; margin-bottom: 30px; flex-wrap: wrap; }
        .filter-btn { padding: 8px 20px; background: white; border: 2px solid #e1e1e1; border-radius: 25px; cursor: pointer; transition: all 0.3s; }
        .filter-btn:hover, .filter-btn.active { background: #667eea; color: white; border-color: #667eea; }
        .ad-container { background: white; padding: 20px; border-radius: 15px; box-shadow: 0 5px 20px rgba(0,0,0,0.1); margin: 30px 0; }
        .ad-container h3 { font-size: 16px; color: #666; margin-bottom: 15px; }
        .ad-placeholder { min-height: 120px; background: #f8f9fa; border-radius: 8px; display: flex; align-items: center; justify-content: center; color: #999; font-size: 14px; }
        .products-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 30px; margin-top: 30px; }
        .product-card { background: white; border-radius: 15px; overflow: hidden; box-shadow: 0 5px 20px rgba(0,0,0,0.1); transition: transform 0.3s, box-shadow 0.3s; position: relative; height: 100%; display: flex; flex-direction: column; }
        .product-card:hover { transform: translateY(-10px); box-shadow: 0 15px 30px rgba(0,0,0,0.15); }
        .product-badge { position: absolute; top: 10px; left: 10px; background: #ff4757; color: white; padding: 4px 10px; border-radius: 4px; font-size: 12px; font-weight: bold; z-index: 2; }
        .product-image { width: 100%; height: 200px; object-fit: cover; transition: transform 0.5s; }
        .product-card:hover .product-image { transform: scale(1.05); }
        .product-content { padding: 20px; flex: 1; display: flex; flex-direction: column; }
        .product-brand { color: #666; font-size: 12px; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 5px; }
        .product-name { font-size: 16px; color: #333; margin-bottom: 10px; line-height: 1.4; height: 45px; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; }
        .product-description { color: #666; font-size: 13px; margin-bottom: 10px; line-height: 1.4; height: 40px; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; }
        .product-rating { display: flex; align-items: center; gap: 5px; margin-bottom: 10px; }
        .rating-stars { color: #ffd700; font-size: 14px; }
        .rating-score { color: #666; font-size: 13px; }
        .sales { color: #999; font-size: 12px; margin-left: 10px; }
        .price-section { display: flex; align-items: center; gap: 10px; margin-bottom: 15px; }
        .current-price { font-size: 20px; font-weight: bold; color: #ff4757; }
        .original-price { font-size: 14px; color: #999; text-decoration: line-through; }
        .discount { background: #ff4757; color: white; padding: 2px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; }
        .product-actions { display: flex; gap: 10px; margin-top: auto; }
        .btn-add-cart { flex: 1; background: #667eea; color: white; border: none; padding: 10px; border-radius: 5px; cursor: pointer; transition: background 0.3s; font-weight: 500; font-size: 14px; }
        .btn-add-cart:hover { background: #764ba2; }
        .btn-favorite { background: #f8f9fa; border: 1px solid #e1e1e1; padding: 10px; border-radius: 5px; cursor: pointer; transition: all 0.3s; width: 45px; display: flex; align-items: center; justify-content: center; }
        .btn-favorite:hover { background: #ff6b6b; color: white; border-color: #ff6b6b; }
        .stock-info { display: flex; align-items: center; gap: 5px; margin-bottom: 15px; font-size: 12px; color: #666; }
        .stock-bar { flex: 1; height: 4px; background: #e1e1e1; border-radius: 2px; overflow: hidden; }
        .stock-fill { height: 100%; background: #4cd964; border-radius: 2px; }
        .product-tags { display: flex; flex-wrap: wrap; gap: 5px; margin-bottom: 15px; }
        .product-tag { background: #f0f5ff; color: #667eea; padding: 2px 8px; border-radius: 3px; font-size: 11px; }
        .breadcrumb { margin-bottom: 30px; color: #666; }
        .breadcrumb a { color: #667eea; text-decoration: none; }
        .breadcrumb a:hover { text-decoration: underline; }
        .search-box { margin-bottom: 30px; display: flex; gap: 10px; }
        .search-input { flex: 1; padding: 12px 20px; border: 2px solid #e1e1e1; border-radius: 8px; font-size: 14px; }
        .search-input:focus { outline: none; border-color: #667eea; }
        .search-btn { background: #667eea; color: white; border: none; padding: 12px 24px; border-radius: 8px; cursor: pointer; font-weight: 500; }
        .empty-state { text-align: center; padding: 80px 20px; color: #666; }
        .empty-state h3 { font-size: 24px; margin-bottom: 10px; color: #333; }
        .category-stats { background: white; border-radius: 10px; padding: 20px; margin-bottom: 30px; box-shadow: 0 3px 10px rgba(0,0,0,0.1); display: flex; justify-content: space-between; align-items: center; }
        .stat-item { text-align: center; }
        .stat-number { font-size: 24px; font-weight: bold; color: #667eea; margin-bottom: 5px; }
        .stat-label { color: #666; font-size: 14px; }
        @media (max-width: 768px) { .header-content { flex-direction: column; gap: 15px; } .nav-links { flex-wrap: wrap; justify-content: center; } .user-info { flex-direction: column; gap: 10px; } .products-grid { grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); } .page-header { flex-direction: column; align-items: flex-start; gap: 20px; } .category-stats { flex-direction: column; gap: 15px; } }
        .ad-text { padding: 15px; background: #f0f5ff; border-radius: 8px; cursor: pointer; transition: all 0.3s; }
        .ad-text:hover { background: #e0ebff; transform: translateY(-2px); }
        .ad-image { width: 100%; border-radius: 8px; cursor: pointer; transition: transform 0.3s; }
        .ad-image:hover { transform: scale(1.02); }
        .ad-video { width: 100%; border-radius: 8px; background: #000; }
    </style>

    <!-- 广告SDK -->
    <script src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>
    <script>
        console.log('🎯 SDK加载监控脚本已初始化');
        window.sdkLoaded = false;
        window.sdkLoadCallbacks = [];
        function onSDKLoaded() {
            console.log('✅ SDK脚本标签加载完成');
            window.sdkLoaded = true;
            window.sdkLoadCallbacks.forEach(callback => {
                try { callback(); } catch (e) { console.error('❌ SDK回调执行失败:', e); }
            });
            window.sdkLoadCallbacks = [];
        }
        setTimeout(onSDKLoaded, 500);
    </script>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header-content">
            <a href="index.jsp" class="logo">购物网站</a>

            <div class="nav-links">
                <a href="index.jsp" class="nav-link">商品分类</a>
                <a href="userInfo.jsp" class="nav-link">个人中心</a>
                <a href="cart" class="nav-link">购物车</a>
                <a href="order" class="nav-link">我的订单</a>
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
        <!-- 面包屑导航 -->
        <div class="breadcrumb">
            <a href="index.jsp">首页</a> &gt;
            <a href="index.jsp">商品分类</a> &gt;
            <span>${categoryName}</span>
        </div>

        <!-- 页面标题 -->
        <div class="page-header">
            <div class="page-title">
                <h1>${categoryName}</h1>
                <p>精选优质商品，满足您的购物需求</p>
            </div>
        </div>

        <!-- 类别统计 -->
        <div class="category-stats">
            <div class="stat-item">
                <div class="stat-number">${products.size()}</div>
                <div class="stat-label">商品总数</div>
            </div>
            <div class="stat-item">
                <div class="stat-number">
                    <fmt:formatNumber value="${totalSales}" pattern="#,###"/>
                </div>
                <div class="stat-label">总销量</div>
            </div>
            <div class="stat-item">
                <div class="stat-number">
                    <fmt:formatNumber value="${averageRating}" pattern="#.#"/>
                </div>
                <div class="stat-label">平均评分</div>
            </div>
        </div>

        <!-- 搜索框 -->
        <div class="search-box">
            <input type="text" class="search-input" placeholder="在${categoryName}中搜索商品..." id="searchInput">
            <button class="search-btn" id="searchBtn">搜索</button>
        </div>

        <!-- 分类筛选 -->
        <div class="category-filter">
            <button class="filter-btn active" data-filter="all">全部商品</button>
            <button class="filter-btn" data-filter="new">新品推荐</button>
            <button class="filter-btn" data-filter="hot">热销商品</button>
            <button class="filter-btn" data-filter="discount">促销优惠</button>
            <select class="filter-btn" style="padding: 6px 20px;" id="sortSelect">
                <option value="">排序方式</option>
                <option value="price_asc">价格从低到高</option>
                <option value="price_desc">价格从高到低</option>
                <option value="sales_desc">销量从高到低</option>
                <option value="rating_desc">评分从高到低</option>
            </select>
        </div>

        <!-- 上方广告位 -->
        <div class="ad-container">
            <h3>🔥 为您推荐</h3>
            <div id="ad-top" class="ad-placeholder">
                广告加载中...
            </div>
        </div>

        <!-- 商品网格 -->
        <c:choose>
            <c:when test="${not empty products and products.size() > 0}">
                <div class="products-grid" id="productsGrid">
                    <c:forEach var="product" items="${products}">
                        <div class="product-card"
                             data-id="${product.id}"
                             data-name="${product.name}"
                             data-brand="${product.brand}"
                             data-price="${product.price}"
                             data-sales="${product.sales}"
                             data-rating="${product.rating}"
                             data-tags="${product.tags}">

                            <c:if test="${product.discount > 0}">
                                <div class="product-badge">-${product.discount}%</div>
                            </c:if>

                            <img src="${product.imageUrl}" alt="${product.name}" class="product-image">

                            <div class="product-content">
                                <div class="product-brand">${product.brand}</div>
                                <h3 class="product-name" title="${product.name}">${product.name}</h3>

                                <div class="product-description" title="${product.description}">
                                        ${product.description}
                                </div>

                                <c:if test="${not empty product.tags}">
                                    <div class="product-tags">
                                        <c:forEach var="tag" items="${product.tags}" end="3">
                                            <span class="product-tag">${tag}</span>
                                        </c:forEach>
                                    </div>
                                </c:if>

                                <div class="product-rating">
                                    <div class="rating-stars">
                                            <%-- 5. 评分星星（使用JSTL代替Java代码）--%>
                                        <c:forEach begin="1" end="5" var="i">
                                            <c:choose>
                                                <c:when test="${product.rating >= i}">★</c:when>
                                                <c:when test="${product.rating > (i-1) && product.rating < i}">⬖</c:when>
                                                <c:otherwise>☆</c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                    <span class="rating-score">
                                        <fmt:formatNumber value="${product.rating}" pattern="#.#"/>
                                    </span>
                                    <span class="sales">已售${product.sales}</span>
                                </div>

                                <div class="stock-info">
                                    <span>库存</span>
                                    <div class="stock-bar">
                                            <%-- 6. 库存进度条（使用EL表达式代替Java代码）--%>
                                        <div class="stock-fill" style="width: ${product.stock > 100 ? 100 : product.stock}%"></div>
                                    </div>
                                    <span>${product.stock}件</span>
                                </div>

                                <div class="price-section">
                                    <span class="current-price">¥<fmt:formatNumber value="${product.price}" pattern="#,##0.00"/></span>
                                    <c:if test="${product.originalPrice > product.price}">
                                        <span class="original-price">¥<fmt:formatNumber value="${product.originalPrice}" pattern="#,##0.00"/></span>
                                        <c:if test="${product.discount > 0}">
                                            <span class="discount">${product.discount}折</span>
                                        </c:if>
                                    </c:if>
                                </div>

                                <div class="product-actions">
                                    <button class="btn-add-cart" onclick="addToCart(${product.id})">
                                        加入购物车
                                    </button>
                                    <button class="btn-favorite" onclick="toggleFavorite(${product.id}, this)">
                                        ♡
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <h3>暂无商品</h3>
                    <p>该类别下暂时没有商品，请稍后再来</p>
                    <button onclick="window.location.href='index.jsp'" style="margin-top: 20px; padding: 10px 20px; background: #667eea; color: white; border: none; border-radius: 5px; cursor: pointer;">
                        返回分类
                    </button>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- 下方广告位 -->
        <div class="ad-container">
            <h3>💡 猜您喜欢</h3>
            <div id="ad-bottom" class="ad-placeholder">
                广告加载中...
            </div>
        </div>
    </div>
</main>

<script>
    // ==================== 全局定义 ====================
    var AD_API_HOST = 'http://10.100.164.17:8080/ad-platform';
    var AD_SITE_ID = 'shopping';

    // ==================== 原有功能保留区 ====================
    // 商品筛选
    document.querySelectorAll('.filter-btn:not(#sortSelect)').forEach(btn => {
        btn.addEventListener('click', function() {
            const filter = this.getAttribute('data-filter');
            document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            filterProducts(filter);
        });
    });

    function filterProducts(filter) {
        const products = document.querySelectorAll('.product-card');
        products.forEach(product => {
            const tags = product.getAttribute('data-tags').toLowerCase();
            const sales = parseInt(product.getAttribute('data-sales'));
            let show = true;
            switch(filter) {
                case 'new': show = sales < 100; break;
                case 'hot': show = sales > 500; break;
                case 'discount': show = tags.includes('促销') || tags.includes('折扣') || product.querySelector('.discount') !== null; break;
                default: show = true;
            }
            product.style.display = show ? 'block' : 'none';
        });
    }

    // 排序功能
    document.getElementById('sortSelect').addEventListener('change', function() {
        const sortType = this.value;
        const productsGrid = document.getElementById('productsGrid');
        const products = Array.from(productsGrid.querySelectorAll('.product-card'));
        products.sort((a, b) => {
            const aPrice = parseFloat(a.getAttribute('data-price'));
            const bPrice = parseFloat(b.getAttribute('data-price'));
            const aSales = parseInt(a.getAttribute('data-sales'));
            const bSales = parseInt(b.getAttribute('data-sales'));
            const aRating = parseFloat(a.getAttribute('data-rating'));
            const bRating = parseFloat(b.getAttribute('data-rating'));
            switch(sortType) {
                case 'price_asc': return aPrice - bPrice;
                case 'price_desc': return bPrice - aPrice;
                case 'sales_desc': return bSales - aSales;
                case 'rating_desc': return bRating - aRating;
                default: return 0;
            }
        });
        products.forEach(product => productsGrid.appendChild(product));
    });

    // 搜索功能
    document.getElementById('searchBtn').addEventListener('click', searchProducts);
    document.getElementById('searchInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') searchProducts();
    });

    function searchProducts() {
        const keyword = document.getElementById('searchInput').value.toLowerCase().trim();
        const products = document.querySelectorAll('.product-card');
        if (!keyword) {
            products.forEach(product => product.style.display = 'block');
            return;
        }
        products.forEach(product => {
            const name = product.getAttribute('data-name').toLowerCase();
            const brand = product.getAttribute('data-brand').toLowerCase();
            const tags = product.getAttribute('data-tags').toLowerCase();
            product.style.display = (name.includes(keyword) || brand.includes(keyword) || tags.includes(keyword)) ? 'block' : 'none';
        });
    }

    // 加入购物车
    function addToCart(productId) {
        const urlParams = new URLSearchParams(window.location.search);
        const category = urlParams.get('category') || 'electronics';
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = 'cart';
        form.style.display = 'none';
        const params = { action: 'add', productId: productId, quantity: 1, category: category };
        for (const key in params) {
            const input = document.createElement('input');
            input.type = 'hidden'; input.name = key; input.value = params[key];
            form.appendChild(input);
        }
        document.body.appendChild(form);
        form.submit();
    }

    // 收藏商品
    function toggleFavorite(productId, button) {
        if (button.textContent === '♡') {
            button.textContent = '♥'; button.style.color = '#ff6b6b';
            alert('已添加到收藏夹！');
        } else {
            button.textContent = '♡'; button.style.color = '#333';
            alert('已从收藏夹移除！');
        }
    }

    // ==================== 广告加载区 ====================
    (function() {
        console.log('🎯 广告加载模块已加载');

        function waitForReady(callback) {
            let attempts = 0;
            const maxAttempts = 50;
            const check = () => {
                attempts++;
                const adTop = document.getElementById('ad-top');
                const adBottom = document.getElementById('ad-bottom');
                const sdkReady = typeof AdPlatformSDK !== 'undefined';
                if (adTop && adBottom && sdkReady) {
                    console.log('✅ DOM和SDK都已准备好');
                    if (typeof AdPlatformSDK.getUserId === 'function') {
                        callback();
                    } else {
                        if (attempts >= maxAttempts) return;
                        setTimeout(check, 100);
                    }
                } else if (attempts >= maxAttempts) {
                    console.error('❌ 等待DOM和SDK超时');
                } else {
                    if (attempts % 10 === 0) {
                        console.log('⏳ 等待中... 第' + attempts + '次检查');
                    }
                    setTimeout(check, 100);
                }
            };
            check();
        }

        function loadAd(containerId, category) {
            console.log('📢 [' + containerId + '] 开始加载广告...');
            try {
                let uid = 'test-uid-' + Date.now();
                if (typeof AdPlatformSDK !== 'undefined' && typeof AdPlatformSDK.getUserId === 'function') {
                    uid = AdPlatformSDK.getUserId() || 'default-' + Date.now();
                }
                const container = document.getElementById(containerId);
                container.innerHTML = '<div style="color: #999; padding: 20px;">🔄 正在加载广告...</div>';
                const apiUrl = AD_API_HOST + '/api/ad/get?uid=' + encodeURIComponent(uid) + '&category=' + encodeURIComponent(category) + '&site=' + encodeURIComponent(AD_SITE_ID);
                console.log('📢 [' + containerId + '] 请求URL: ' + apiUrl);
                fetch(apiUrl)
                    .then(response => {
                        console.log('📢 [' + containerId + '] HTTP状态码: ' + response.status);
                        if (!response.ok) throw new Error('HTTP ' + response.status + ' - ' + response.statusText);
                        return response.json();
                    })
                    .then(data => {
                        console.log('✅ [' + containerId + '] API返回:', data);
                        if (data && data.success === true && data.ad) {
                            container.innerHTML = '';
                            AdPlatformSDK.renderAd(containerId, data.ad);
                            console.log('✅ [' + containerId + '] 渲染成功！');
                            reportAdImpression(data.ad.id, uid, category);
                        } else {
                            const msg = (data && data.message) ? data.message : '未知原因';
                            console.warn('⚠️ [' + containerId + '] 无广告: ' + msg);
                            container.innerHTML = '<div style="color: #999; padding: 20px;">暂无可推荐广告（' + msg + '）</div>';
                        }
                    })
                    .catch(error => {
                        console.error('❌ [' + containerId + '] 加载失败:', error);
                        container.innerHTML = '<div style="color: #e74c3c; padding: 20px;">广告加载失败（' + error.message + '）</div>';
                    });
            } catch (e) {
                console.error('❌ [' + containerId + '] 初始化异常:', e);
                document.getElementById(containerId).innerHTML = '<div style="color: #e74c3c; padding: 20px;">广告初始化异常</div>';
            }
        }

        function reportAdImpression(adId, uid, category) {
            try {
                const url = AD_API_HOST + '/api/track/impression?uid=' + encodeURIComponent(uid) + '&adId=' + encodeURIComponent(adId) + '&site=' + encodeURIComponent(AD_SITE_ID) + '&category=' + encodeURIComponent(category);
                console.log('📢 上报展示: ' + url);
                fetch(url)
                    .then(() => console.log('📢 广告 ' + adId + ' 上报成功'))
                    .catch(err => console.error('❌ 广告 ' + adId + ' 上报失败:', err));
            } catch (e) {
                console.error('❌ 上报异常:', e);
            }
        }

        function initAds() {
            console.log('🚀 开始初始化广告系统...');
            const urlParams = new URLSearchParams(window.location.search);
            const category = urlParams.get('category') || 'electronics';
            console.log('📢 当前页面分类: ' + category);
            setTimeout(() => {
                console.log('⏰ 延迟500ms完成，开始加载广告位');
                loadAd('ad-top', category);
                loadAd('ad-bottom', category);
            }, 500);
        }

        waitForReady(initAds);
    })();

    // 页面初始化
    document.addEventListener('DOMContentLoaded', function() {
        const sortSelect = document.getElementById('sortSelect');
        if (sortSelect.value) {
            sortSelect.dispatchEvent(new Event('change'));
        }
    });
</script>
</body>
</html>