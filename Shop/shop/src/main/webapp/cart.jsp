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
    <title>购物网站 - 购物车</title>
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

        .cart-container {
            display: grid;
            grid-template-columns: 1fr 350px;
            gap: 30px;
        }

        .cart-items {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .cart-summary {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            height: fit-content;
        }

        .cart-empty {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }

        .cart-empty h3 {
            font-size: 24px;
            margin-bottom: 15px;
            color: #333;
        }

        .cart-empty p {
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

        .cart-item {
            display: grid;
            grid-template-columns: auto 1fr auto;
            gap: 20px;
            padding: 20px 0;
            border-bottom: 1px solid #eee;
            align-items: center;
        }

        .cart-item:last-child {
            border-bottom: none;
        }

        .item-check {
            display: flex;
            align-items: center;
        }

        .item-check input[type="checkbox"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
        }

        .item-info {
            display: grid;
            grid-template-columns: 100px 1fr;
            gap: 15px;
            align-items: center;
        }

        .item-image {
            width: 100px;
            height: 100px;
            border-radius: 8px;
            object-fit: cover;
        }

        .item-details h4 {
            font-size: 16px;
            color: #333;
            margin-bottom: 5px;
            line-height: 1.4;
        }

        .item-brand {
            color: #666;
            font-size: 12px;
            margin-bottom: 5px;
        }

        .item-tags {
            display: flex;
            gap: 5px;
            margin-bottom: 10px;
            flex-wrap: wrap;
        }

        .item-tag {
            background: #f0f5ff;
            color: #667eea;
            padding: 2px 8px;
            border-radius: 3px;
            font-size: 11px;
        }

        .item-rating {
            display: flex;
            align-items: center;
            gap: 5px;
            font-size: 12px;
            color: #ffd700;
        }

        .item-rating .score {
            color: #666;
            margin-left: 5px;
        }

        .item-actions {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .item-price {
            text-align: right;
            min-width: 100px;
        }

        .current-price {
            font-size: 18px;
            font-weight: bold;
            color: #ff4757;
            margin-bottom: 5px;
        }

        .original-price {
            font-size: 14px;
            color: #999;
            text-decoration: line-through;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            border: 1px solid #e1e1e1;
            border-radius: 5px;
            overflow: hidden;
        }

        .quantity-btn {
            width: 30px;
            height: 30px;
            background: #f8f9fa;
            border: none;
            cursor: pointer;
            font-size: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .quantity-btn:hover {
            background: #e9ecef;
        }

        .quantity-input {
            width: 40px;
            height: 30px;
            border: none;
            text-align: center;
            font-size: 14px;
        }

        .quantity-input:focus {
            outline: none;
        }

        .btn-remove {
            background: none;
            border: none;
            color: #999;
            cursor: pointer;
            font-size: 14px;
            padding: 5px;
        }

        .btn-remove:hover {
            color: #ff4757;
        }

        .cart-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .cart-header h2 {
            font-size: 20px;
            color: #333;
        }

        .btn-clear {
            background: #f8f9fa;
            color: #666;
            border: 1px solid #e1e1e1;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-clear:hover {
            background: #e9ecef;
        }

        .summary-title {
            font-size: 20px;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
            color: #666;
        }

        .summary-row.total {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            padding-top: 15px;
            border-top: 2px solid #f0f0f0;
            margin-top: 15px;
        }

        .summary-row.total .amount {
            color: #ff4757;
        }

        .btn-checkout {
            width: 100%;
            background: #667eea;
            color: white;
            border: none;
            padding: 14px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            margin-top: 20px;
            transition: background 0.3s;
        }

        .btn-checkout:hover {
            background: #764ba2;
        }

        .btn-checkout:disabled {
            background: #ccc;
            cursor: not-allowed;
        }

        .delivery-info {
            margin-top: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 8px;
            font-size: 14px;
        }

        .delivery-info h4 {
            color: #333;
            margin-bottom: 10px;
        }

        .delivery-info p {
            color: #666;
            margin-bottom: 5px;
        }

        @media (max-width: 992px) {
            .cart-container {
                grid-template-columns: 1fr;
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

            .cart-item {
                grid-template-columns: 1fr;
                gap: 15px;
            }

            .item-info {
                grid-template-columns: 80px 1fr;
            }

            .item-image {
                width: 80px;
                height: 80px;
            }

            .item-actions {
                justify-content: space-between;
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
                <a href="cart" class="nav-link active">购物车</a>  <!-- ✅ 已修复：cart -->
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
            <h1>我的购物车</h1>
            <p>查看和管理您的购物车商品</p>
        </div>

        <c:choose>
            <c:when test="${empty cartItems or cartItems.size() == 0}">
                <div class="cart-empty">
                    <h3>购物车空空如也</h3>
                    <p>快去挑选心仪的商品吧！</p>
                    <a href="index.jsp" class="btn-shopping">去逛逛</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cart-container">
                    <!-- 购物车商品列表 -->
                    <div class="cart-items">
                        <div class="cart-header">
                            <h2>购物车（${cartItems.size()}件商品）</h2>
                            <form action="cart" method="post" onsubmit="return confirm('确定要清空购物车吗？')">
                                <input type="hidden" name="action" value="clear">
                                <button type="submit" class="btn-clear">清空购物车</button>
                            </form>
                        </div>

                        <c:forEach var="item" items="${cartItems}">
                            <div class="cart-item" data-id="${item.id}">
                                <div class="item-check">
                                    <input type="checkbox" name="cartItem" value="${item.id}" checked>
                                </div>

                                <div class="item-info">
                                    <img src="${item.product.imageUrl}" alt="${item.product.name}" class="item-image">
                                    <div class="item-details">
                                        <h4 title="${item.product.name}">${item.product.name}</h4>
                                        <div class="item-brand">${item.product.brand}</div>

                                        <c:if test="${not empty item.product.tags}">
                                            <div class="item-tags">
                                                <c:forEach var="tag" items="${item.product.tags}" end="3">
                                                    <span class="item-tag">${tag}</span>
                                                </c:forEach>
                                            </div>
                                        </c:if>

                                        <div class="item-rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= item.product.rating}">★</c:when>
                                                    <c:otherwise>☆</c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <span class="score">
                                                <fmt:formatNumber value="${item.product.rating}" pattern="#.#"/>
                                            </span>
                                        </div>

                                        <div class="stock-info" style="font-size: 12px; color: ${item.product.stock > 0 ? '#4cd964' : '#ff4757'}">
                                            库存：${item.product.stock}件
                                        </div>
                                    </div>
                                </div>

                                <div class="item-actions">
                                    <div class="item-price">
                                        <div class="current-price">
                                            ¥<fmt:formatNumber value="${item.product.price}" pattern="#,##0.00"/>
                                        </div>
                                        <c:if test="${item.product.originalPrice > item.product.price}">
                                            <div class="original-price">
                                                ¥<fmt:formatNumber value="${item.product.originalPrice}" pattern="#,##0.00"/>
                                            </div>
                                        </c:if>
                                    </div>

                                    <div class="quantity-control">
                                        <button type="button" class="quantity-btn minus" onclick="updateQuantity(${item.id}, ${item.quantity - 1})">-</button>
                                        <input type="text" class="quantity-input" value="${item.quantity}"
                                               data-id="${item.id}" onchange="updateQuantity(${item.id}, this.value)">
                                        <button type="button" class="quantity-btn plus" onclick="updateQuantity(${item.id}, ${item.quantity + 1})">+</button>
                                    </div>

                                    <form action="cart" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="cartId" value="${item.id}">
                                        <button type="submit" class="btn-remove" onclick="return confirm('确定要删除吗？')">删除</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- 购物车结算 -->
                    <div class="cart-summary">
                        <h3 class="summary-title">订单结算</h3>

                        <div class="summary-row">
                            <span>商品金额</span>
                            <span>¥<fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/></span>
                        </div>

                        <div class="summary-row">
                            <span>运费</span>
                            <span>¥0.00</span>
                        </div>

                        <div class="summary-row">
                            <span>优惠</span>
                            <span>-¥0.00</span>
                        </div>

                        <div class="summary-row total">
                            <span>应付总额</span>
                            <span class="amount">¥<fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/></span>
                        </div>

                        <div class="delivery-info">
                            <h4>配送信息</h4>
                            <p>• 全场满99元包邮</p>
                            <p>• 预计1-3个工作日送达</p>
                            <p>• 支持7天无理由退货</p>
                        </div>

                        <form action="order" method="get">
                            <input type="hidden" name="action" value="create">
                            <button type="submit" class="btn-checkout">去结算</button>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<script>
    // 更新购物车商品数量
    function updateQuantity(cartId, newQuantity) {
        if (newQuantity < 1) {
            if (confirm('确定要删除该商品吗？')) {
                document.querySelector(`form[data-id="${cartId}"]`).submit();
            }
            return;
        }

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = 'cart';

        const actionInput = document.createElement('input');
        actionInput.type = 'hidden';
        actionInput.name = 'action';
        actionInput.value = 'update';
        form.appendChild(actionInput);

        const cartIdInput = document.createElement('input');
        cartIdInput.type = 'hidden';
        cartIdInput.name = 'cartId';
        cartIdInput.value = cartId;
        form.appendChild(cartIdInput);

        const quantityInput = document.createElement('input');
        quantityInput.type = 'hidden';
        quantityInput.name = 'quantity';
        quantityInput.value = newQuantity;
        form.appendChild(quantityInput);

        document.body.appendChild(form);
        form.submit();
    }

    // 全选功能
    document.addEventListener('DOMContentLoaded', function() {
        const checkboxes = document.querySelectorAll('input[name="cartItem"]');
        const selectAll = document.getElementById('selectAll');

        if (selectAll) {
            selectAll.addEventListener('change', function() {
                checkboxes.forEach(checkbox => {
                    checkbox.checked = this.checked;
                });
                updateTotal();
            });
        }

        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', updateTotal);
        });

        // 输入框变化事件
        document.querySelectorAll('.quantity-input').forEach(input => {
            input.addEventListener('blur', function() {
                const cartId = this.getAttribute('data-id');
                const quantity = parseInt(this.value) || 1;
                updateQuantity(cartId, quantity);
            });
        });
    });

    // 更新总金额
    function updateTotal() {
        let total = 0;
        const selectedItems = document.querySelectorAll('input[name="cartItem"]:checked');

        selectedItems.forEach(item => {
            const cartItem = item.closest('.cart-item');
            const price = parseFloat(cartItem.querySelector('.current-price').textContent.replace('¥', '').replace(',', ''));
            const quantity = parseInt(cartItem.querySelector('.quantity-input').value);
            total += price * quantity;
        });

        document.querySelectorAll('.total .amount').forEach(el => {
            el.textContent = '¥' + total.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        });
    }
</script>
</body>
</html>