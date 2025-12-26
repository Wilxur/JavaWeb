<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${empty sessionScope.isLoggedIn or not sessionScope.isLoggedIn}">
    <c:redirect url="/login" />
</c:if>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>购物网站 - 我的订单</title>
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

        .orders-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
            cursor: pointer;
            transition: transform 0.3s;
        }

        .stat-card:hover {
            transform: translateY(-5px);
        }

        .stat-card.active {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .stat-card.active .stat-number,
        .stat-card.active .stat-label {
            color: white;
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

        .orders-filter {
            display: flex;
            gap: 10px;
            margin-bottom: 30px;
            flex-wrap: wrap;
            align-items: center;
            justify-content: space-between;
        }

        .filter-btns {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .filter-btn {
            padding: 8px 20px;
            background: white;
            border: 2px solid #e1e1e1;
            border-radius: 25px;
            cursor: pointer;
            transition: all 0.3s;
        }

        .filter-btn:hover,
        .filter-btn.active {
            background: #667eea;
            color: white;
            border-color: #667eea;
        }

        .search-box {
            display: flex;
            gap: 10px;
        }

        .search-input {
            padding: 8px 15px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            font-size: 14px;
            min-width: 250px;
        }

        .search-input:focus {
            outline: none;
            border-color: #667eea;
        }

        .search-btn {
            background: #667eea;
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 8px;
            cursor: pointer;
        }

        .orders-list {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .order-empty {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }

        .order-empty h3 {
            font-size: 24px;
            margin-bottom: 15px;
            color: #333;
        }

        .order-empty p {
            margin-bottom: 30px;
        }

        .btn-shopping {
            background: #667eea;
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            display: inline-block;
        }

        .btn-shopping:hover {
            background: #764ba2;
        }

        .order-item {
            border-bottom: 1px solid #eee;
            transition: background 0.3s;
        }

        .order-item:hover {
            background: #fafafa;
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            background: #f8f9fa;
        }

        .order-info {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .order-number {
            font-size: 16px;
            font-weight: bold;
            color: #333;
        }

        .order-time {
            color: #666;
            font-size: 12px;
        }

        .order-status {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
            color: white;
        }

        .status-pending {
            background: #ffa502;
        }

        .status-paid {
            background: #2ed573;
        }

        .status-shipped {
            background: #1e90ff;
        }

        .status-delivered {
            background: #747d8c;
        }

        .status-cancelled {
            background: #ff4757;
        }

        .order-content {
            padding: 20px;
            display: grid;
            grid-template-columns: 1fr auto;
            gap: 20px;
        }

        .order-products {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }

        .product-preview {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 10px;
            border: 1px solid #eee;
            border-radius: 8px;
            min-width: 200px;
        }

        .product-image {
            width: 60px;
            height: 60px;
            border-radius: 8px;
            object-fit: cover;
        }

        .product-info h4 {
            font-size: 14px;
            color: #333;
            margin-bottom: 5px;
            line-height: 1.4;
        }

        .product-price {
            color: #ff4757;
            font-weight: bold;
            font-size: 14px;
        }

        .product-quantity {
            color: #666;
            font-size: 12px;
        }

        .order-actions {
            display: flex;
            flex-direction: column;
            gap: 10px;
            min-width: 150px;
        }

        .order-total {
            text-align: right;
            margin-bottom: 10px;
        }

        .order-total .label {
            color: #666;
            font-size: 12px;
        }

        .order-total .amount {
            font-size: 20px;
            font-weight: bold;
            color: #ff4757;
        }

        .action-btns {
            display: flex;
            gap: 10px;
        }

        .btn-action {
            flex: 1;
            padding: 8px;
            border: 1px solid #e1e1e1;
            border-radius: 5px;
            cursor: pointer;
            font-size: 12px;
            background: white;
            transition: all 0.3s;
        }

        .btn-action.primary {
            background: #667eea;
            color: white;
            border-color: #667eea;
        }

        .btn-action.primary:hover {
            background: #764ba2;
        }

        .btn-action:hover {
            background: #f8f9fa;
        }

        .order-footer {
            padding: 15px 20px;
            background: #f8f9fa;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 12px;
            color: #666;
        }

        .order-address {
            max-width: 300px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .order-payment {
            color: #667eea;
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

            .orders-filter {
                flex-direction: column;
                align-items: stretch;
            }

            .search-box {
                width: 100%;
            }

            .search-input {
                flex: 1;
            }

            .order-content {
                grid-template-columns: 1fr;
                gap: 15px;
            }

            .order-products {
                flex-direction: column;
            }

            .product-preview {
                min-width: 100%;
            }

            .order-actions {
                min-width: 100%;
            }

            .order-footer {
                flex-direction: column;
                gap: 10px;
                align-items: flex-start;
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
                <a href="userInfo.jsp" class="nav-link">个人中心</a>
                <a href="cart" class="nav-link">购物车</a>
                <a href="order" class="nav-link active">我的订单</a>
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
            <h1>我的订单</h1>
            <p>查看和管理您的所有订单</p>
        </div>

        <!-- 订单统计 -->
        <div class="orders-stats">
            <div class="stat-card" onclick="filterOrders('all')">
                <div class="stat-number">${stats[0] + stats[1] + stats[2] + stats[3] + stats[4]}</div>
                <div class="stat-label">全部订单</div>
            </div>
            <div class="stat-card" onclick="filterOrders('pending')">
                <div class="stat-number">${stats[0]}</div>
                <div class="stat-label">待付款</div>
            </div>
            <div class="stat-card" onclick="filterOrders('paid')">
                <div class="stat-number">${stats[1]}</div>
                <div class="stat-label">待发货</div>
            </div>
            <div class="stat-card" onclick="filterOrders('shipped')">
                <div class="stat-number">${stats[2]}</div>
                <div class="stat-label">待收货</div>
            </div>
            <div class="stat-card" onclick="filterOrders('delivered')">
                <div class="stat-number">${stats[3]}</div>
                <div class="stat-label">已完成</div>
            </div>
        </div>

        <!-- 订单筛选 -->
        <div class="orders-filter">
            <div class="filter-btns">
                <button class="filter-btn active" onclick="filterOrders('all')">全部</button>
                <button class="filter-btn" onclick="filterOrders('pending')">待付款</button>
                <button class="filter-btn" onclick="filterOrders('paid')">待发货</button>
                <button class="filter-btn" onclick="filterOrders('shipped')">待收货</button>
                <button class="filter-btn" onclick="filterOrders('delivered')">已完成</button>
                <button class="filter-btn" onclick="filterOrders('cancelled')">已取消</button>
            </div>

            <div class="search-box">
                <input type="text" class="search-input" placeholder="搜索订单号或商品名称" id="searchInput">
                <button class="search-btn" onclick="searchOrders()">搜索</button>
            </div>
        </div>

        <!-- 订单列表 -->
        <div class="orders-list" id="ordersList">
            <c:choose>
                <c:when test="${empty orders or orders.size() == 0}">
                    <div class="order-empty">
                        <h3>暂无订单</h3>
                        <p>您还没有任何订单，快去选购商品吧！</p>
                        <a href="index.jsp" class="btn-shopping">去逛逛</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="order" items="${orders}">
                        <div class="order-item" data-id="${order.id}" data-number="${order.orderNumber}" data-status="${order.status}">
                            <div class="order-header">
                                <div class="order-info">
                                    <div class="order-number">订单号：${order.orderNumber}</div>
                                    <div class="order-time">
                                        下单时间：<fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </div>
                                </div>
                                <div class="order-status ${order.statusClass}">
                                        ${order.statusText}
                                </div>
                            </div>

                            <div class="order-content">
                                <div class="order-products">
                                    <c:forEach var="item" items="${order.items}" end="3">
                                        <div class="product-preview">
                                            <img src="${item.productImage}" alt="${item.productName}" class="product-image">
                                            <div class="product-info">
                                                <h4 title="${item.productName}">${item.productName}</h4>
                                                <div class="product-price">¥<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/></div>
                                                <div class="product-quantity">× ${item.quantity}</div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${order.items.size() > 3}">
                                        <div class="product-preview" style="justify-content: center;">
                                            等${order.items.size() - 3}件商品
                                        </div>
                                    </c:if>
                                </div>

                                <div class="order-actions">
                                    <div class="order-total">
                                        <div class="label">订单总额</div>
                                        <div class="amount">¥<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></div>
                                    </div>

                                    <div class="action-btns">
                                        <a href="order?action=view&id=${order.id}" class="btn-action">查看详情</a>
                                        <c:if test="${order.status == 'pending'}">
                                            <form action="order" method="post" style="display: inline;">
                                                <input type="hidden" name="action" value="cancel">
                                                <input type="hidden" name="orderId" value="${order.id}">
                                                <button type="submit" class="btn-action" onclick="return confirm('确定要取消订单吗？')">取消订单</button>
                                            </form>
                                            <button class="btn-action primary" onclick="payOrder(${order.id})">立即支付</button>
                                        </c:if>
                                        <c:if test="${order.status == 'shipped'}">
                                            <button class="btn-action primary" onclick="confirmReceipt(${order.id})">确认收货</button>
                                        </c:if>
                                        <c:if test="${order.status == 'delivered'}">
                                            <button class="btn-action" onclick="reviewOrder(${order.id})">评价</button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <div class="order-footer">
                                <div class="order-address" title="收货地址：${order.shippingAddress}">
                                    收货地址：${order.shippingAddress}
                                </div>
                                <div class="order-payment">
                                    支付方式：${order.paymentMethod}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>

<script>
    // 订单筛选
    let currentFilter = 'all';

    function filterOrders(status) {
        currentFilter = status;

        // 更新按钮状态
        document.querySelectorAll('.filter-btn').forEach(btn => {
            btn.classList.remove('active');
        });
        document.querySelector(`.filter-btn[onclick="filterOrders('${status}')"]`).classList.add('active');

        // 筛选订单
        const orders = document.querySelectorAll('.order-item');
        orders.forEach(order => {
            const orderStatus = order.getAttribute('data-status');

            if (status === 'all' || orderStatus === status) {
                order.style.display = 'block';
            } else {
                order.style.display = 'none';
            }
        });
    }

    // 搜索订单
    function searchOrders() {
        const keyword = document.getElementById('searchInput').value.toLowerCase().trim();
        const orders = document.querySelectorAll('.order-item');

        if (!keyword) {
            // 如果没有搜索词，恢复当前筛选
            filterOrders(currentFilter);
            return;
        }

        orders.forEach(order => {
            const orderNumber = order.getAttribute('data-number').toLowerCase();
            const orderId = order.getAttribute('data-id');
            const orderStatus = order.getAttribute('data-status');

            // 检查是否匹配搜索条件
            const matchNumber = orderNumber.includes(keyword);
            const matchId = orderId.includes(keyword);

            // 检查是否符合当前筛选
            const filterMatch = currentFilter === 'all' || orderStatus === currentFilter;

            if ((matchNumber || matchId) && filterMatch) {
                order.style.display = 'block';
            } else {
                order.style.display = 'none';
            }
        });
    }

    // 支付订单
    function payOrder(orderId) {
        if (confirm('确定要支付该订单吗？')) {
            // 这里应该跳转到支付页面或调用支付接口
            alert('支付功能正在开发中...');
            // 实际开发中，这里应该重定向到支付页面或调用支付API
            // window.location.href = `pay?orderId=${orderId}`;
        }
    }

    // 确认收货
    function confirmReceipt(orderId) {
        if (confirm('请确认您已收到商品')) {
            // 这里应该调用确认收货接口
            alert('确认收货功能正在开发中...');
            // 实际开发中，这里应该发送AJAX请求到服务器
            // fetch(`order?action=confirm&id=${orderId}`, { method: 'POST' })
            //     .then(response => location.reload());
        }
    }

    // 评价订单
    function reviewOrder(orderId) {
        alert('评价功能正在开发中...');
        // 实际开发中，这里应该跳转到评价页面
        // window.location.href = `review?orderId=${orderId}`;
    }

    // 页面加载时初始化
    document.addEventListener('DOMContentLoaded', function() {
        // 统计卡片点击效果
        document.querySelectorAll('.stat-card').forEach(card => {
            card.addEventListener('click', function() {
                document.querySelectorAll('.stat-card').forEach(c => c.classList.remove('active'));
                this.classList.add('active');
            });
        });

        // 搜索框回车键支持
        document.getElementById('searchInput').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchOrders();
            }
        });
    });
</script>
</body>
</html>