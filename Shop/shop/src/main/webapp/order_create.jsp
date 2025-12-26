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
    <title>购物网站 - 确认订单</title>
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

        .checkout-steps {
            display: flex;
            justify-content: center;
            margin-bottom: 40px;
        }

        .step {
            display: flex;
            align-items: center;
            color: #999;
            font-size: 14px;
        }

        .step.active {
            color: #667eea;
            font-weight: bold;
        }

        .step-number {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background: #f0f0f0;
            color: #999;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 10px;
        }

        .step.active .step-number {
            background: #667eea;
            color: white;
        }

        .step-line {
            width: 100px;
            height: 2px;
            background: #f0f0f0;
            margin: 0 20px;
        }

        .step.active .step-line {
            background: #667eea;
        }

        .checkout-container {
            display: grid;
            grid-template-columns: 1fr 350px;
            gap: 30px;
        }

        .order-form {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .order-summary {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            height: fit-content;
        }

        .section-title {
            font-size: 18px;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
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

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .payment-methods {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
            gap: 10px;
            margin-top: 10px;
        }

        .payment-method {
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            padding: 15px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s;
        }

        .payment-method:hover {
            border-color: #667eea;
        }

        .payment-method.selected {
            border-color: #667eea;
            background: #f0f5ff;
        }

        .payment-method .icon {
            font-size: 24px;
            margin-bottom: 5px;
        }

        .payment-method .name {
            font-size: 14px;
            color: #333;
        }

        .order-items {
            margin-top: 20px;
        }

        .order-item {
            display: grid;
            grid-template-columns: 80px 1fr auto;
            gap: 15px;
            padding: 15px 0;
            border-bottom: 1px solid #eee;
            align-items: center;
        }

        .order-item:last-child {
            border-bottom: none;
        }

        .item-image {
            width: 80px;
            height: 80px;
            border-radius: 8px;
            object-fit: cover;
        }

        .item-info h4 {
            font-size: 14px;
            color: #333;
            margin-bottom: 5px;
            line-height: 1.4;
        }

        .item-price {
            text-align: right;
            min-width: 100px;
        }

        .item-price .current {
            font-size: 16px;
            font-weight: bold;
            color: #ff4757;
        }

        .item-price .quantity {
            color: #666;
            font-size: 12px;
            margin-top: 5px;
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

        .btn-submit {
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

        .btn-submit:hover {
            background: #764ba2;
        }

        .btn-back {
            display: inline-block;
            background: #f8f9fa;
            color: #666;
            border: 1px solid #e1e1e1;
            padding: 12px 24px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            margin-top: 15px;
        }

        .btn-back:hover {
            background: #e9ecef;
        }

        @media (max-width: 992px) {
            .checkout-container {
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

            .form-row {
                grid-template-columns: 1fr;
            }

            .payment-methods {
                grid-template-columns: repeat(2, 1fr);
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
        <!-- 步骤指示器 -->
        <div class="checkout-steps">
            <div class="step active">
                <div class="step-number">1</div>
                <span>确认订单</span>
                <div class="step-line"></div>
            </div>
            <div class="step">
                <div class="step-number">2</div>
                <span>付款</span>
                <div class="step-line"></div>
            </div>
            <div class="step">
                <div class="step-number">3</div>
                <span>完成</span>
            </div>
        </div>

        <div class="checkout-container">
            <!-- 订单表单 -->
            <div class="order-form">
                <h2 class="section-title">收货信息</h2>

                <form action="order" method="post" id="orderForm">
                    <input type="hidden" name="action" value="create">

                    <div class="form-group">
                        <label for="contactName">收货人姓名 *</label>
                        <input type="text" id="contactName" name="contactName"
                               class="form-control" value="${sessionScope.user.username}" required>
                    </div>

                    <div class="form-group">
                        <label for="contactPhone">联系电话 *</label>
                        <input type="tel" id="contactPhone" name="contactPhone"
                               class="form-control" value="${sessionScope.user.phone}" required>
                    </div>

                    <div class="form-group">
                        <label for="shippingAddress">收货地址 *</label>
                        <textarea id="shippingAddress" name="shippingAddress"
                                  class="form-control" rows="3" required>请输入详细收货地址</textarea>
                    </div>

                    <h2 class="section-title" style="margin-top: 30px;">支付方式</h2>

                    <div class="form-group">
                        <div class="payment-methods">
                            <div class="payment-method selected" data-method="alipay">
                                <div class="icon">💰</div>
                                <div class="name">支付宝</div>
                                <input type="radio" name="paymentMethod" value="alipay" checked style="display: none;">
                            </div>
                            <div class="payment-method" data-method="wechat">
                                <div class="icon">💳</div>
                                <div class="name">微信支付</div>
                                <input type="radio" name="paymentMethod" value="wechat" style="display: none;">
                            </div>
                            <div class="payment-method" data-method="bank">
                                <div class="icon">🏦</div>
                                <div class="name">银行卡</div>
                                <input type="radio" name="paymentMethod" value="bank" style="display: none;">
                            </div>
                            <div class="payment-method" data-method="cash">
                                <div class="icon">💵</div>
                                <div class="name">货到付款</div>
                                <input type="radio" name="paymentMethod" value="cash" style="display: none;">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="note">备注信息</label>
                        <textarea id="note" name="note" class="form-control" rows="3"
                                  placeholder="如有特殊需求，请在此备注"></textarea>
                    </div>
                </form>

                <a href="cart" class="btn-back">返回购物车</a>
            </div>

            <!-- 订单摘要 -->
            <div class="order-summary">
                <h2 class="section-title">订单信息</h2>

                <div class="order-items">
                    <c:forEach var="item" items="${cartItems}">
                        <div class="order-item">
                            <img src="${item.product.imageUrl}" alt="${item.product.name}" class="item-image">
                            <div class="item-info">
                                <h4 title="${item.product.name}">${item.product.name}</h4>
                                <div style="color: #666; font-size: 12px;">${item.product.brand}</div>
                            </div>
                            <div class="item-price">
                                <div class="current">
                                    ¥<fmt:formatNumber value="${item.product.price}" pattern="#,##0.00"/>
                                </div>
                                <div class="quantity">× ${item.quantity}</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

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

                <button type="submit" form="orderForm" class="btn-submit">提交订单</button>

                <div style="margin-top: 20px; padding: 15px; background: #f8f9fa; border-radius: 8px; font-size: 12px; color: #666;">
                    <p>• 提交订单即表示您同意我们的《用户协议》</p>
                    <p>• 订单提交后30分钟内未支付将自动取消</p>
                    <p>• 如有问题请联系客服：400-123-4567</p>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    // 支付方式选择
    document.querySelectorAll('.payment-method').forEach(method => {
        method.addEventListener('click', function() {
            // 移除所有选中状态
            document.querySelectorAll('.payment-method').forEach(m => {
                m.classList.remove('selected');
                m.querySelector('input[type="radio"]').checked = false;
            });

            // 添加当前选中状态
            this.classList.add('selected');
            this.querySelector('input[type="radio"]').checked = true;
        });
    });

    // 表单验证
    document.getElementById('orderForm').addEventListener('submit', function(e) {
        const contactName = document.getElementById('contactName').value.trim();
        const contactPhone = document.getElementById('contactPhone').value.trim();
        const shippingAddress = document.getElementById('shippingAddress').value.trim();

        if (!contactName) {
            e.preventDefault();
            alert('请输入收货人姓名');
            return false;
        }

        if (!contactPhone) {
            e.preventDefault();
            alert('请输入联系电话');
            return false;
        }

        if (!shippingAddress || shippingAddress === '请输入详细收货地址') {
            e.preventDefault();
            alert('请输入收货地址');
            return false;
        }

        if (!/^1[3-9]\d{9}$/.test(contactPhone)) {
            e.preventDefault();
            alert('请输入有效的手机号码');
            return false;
        }

        return true;
    });
</script>
</body>
</html>