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
    <title>购物网站 - 订单详情</title>
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

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 40px;
            padding-bottom: 20px;
            border-bottom: 2px solid #eee;
        }

        .page-title h1 {
            font-size: 32px;
            color: #333;
            margin-bottom: 10px;
        }

        .page-title p {
            color: #666;
        }

        .order-status-bar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 40px;
            position: relative;
        }

        .status-step {
            display: flex;
            flex-direction: column;
            align-items: center;
            position: relative;
            z-index: 2;
        }

        .status-icon {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background: #f0f0f0;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
            margin-bottom: 10px;
            color: #999;
        }

        .status-step.active .status-icon {
            background: #667eea;
            color: white;
        }

        .status-step.completed .status-icon {
            background: #2ed573;
            color: white;
        }

        .status-label {
            font-size: 14px;
            color: #999;
        }

        .status-step.active .status-label {
            color: #667eea;
            font-weight: bold;
        }

        .status-step.completed .status-label {
            color: #2ed573;
        }

        .status-line {
            position: absolute;
            top: 25px;
            left: 0;
            right: 0;
            height: 2px;
            background: #f0f0f0;
            z-index: 1;
        }

        .status-line-fill {
            position: absolute;
            top: 25px;
            left: 0;
            height: 2px;
            background: #2ed573;
            z-index: 1;
            transition: width 0.3s;
        }

        .order-detail {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 30px;
        }

        .order-info {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .order-actions {
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

        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .info-item {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .info-label {
            font-size: 12px;
            color: #666;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .info-value {
            font-size: 16px;
            color: #333;
            font-weight: 500;
        }

        .products-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        .products-table th {
            background: #f8f9fa;
            padding: 12px 15px;
            text-align: left;
            font-weight: bold;
            color: #333;
            border-bottom: 2px solid #f0f0f0;
        }

        .products-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }

        .product-cell {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .product-image {
            width: 60px;
            height: 60px;
            border-radius: 8px;
            object-fit: cover;
        }

        .product-name {
            font-size: 14px;
            color: #333;
            line-height: 1.4;
        }

        .product-brand {
            font-size: 12px;
            color: #666;
            margin-top: 5px;
        }

        .price-cell {
            font-size: 16px;
            color: #333;
            font-weight: bold;
        }

        .quantity-cell {
            color: #666;
        }

        .subtotal-cell {
            font-size: 16px;
            color: #ff4757;
            font-weight: bold;
        }

        .summary-section {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 2px solid #f0f0f0;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            color: #666;
        }

        .summary-row.total {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            margin-top: 10px;
            padding-top: 10px;
            border-top: 2px solid #f0f0f0;
        }

        .summary-row.total .amount {
            color: #ff4757;
        }

        .action-buttons {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .btn-action {
            width: 100%;
            padding: 12px;
            border: 1px solid #e1e1e1;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
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

        .timeline {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 2px solid #f0f0f0;
        }

        .timeline-item {
            display: flex;
            gap: 15px;
            padding: 10px 0;
            position: relative;
        }

        .timeline-item:before {
            content: '';
            position: absolute;
            left: 0;
            top: 20px;
            width: 8px;
            height: 8px;
            background: #667eea;
            border-radius: 50%;
        }

        .timeline-content {
            flex: 1;
            margin-left: 20px;
        }

        .timeline-time {
            font-size: 12px;
            color: #666;
            margin-bottom: 5px;
        }

        .timeline-text {
            font-size: 14px;
            color: #333;
        }

        .btn-back {
            display: inline-block;
            background: #f8f9fa;
            color: #666;
            border: 1px solid #e1e1e1;
            padding: 10px 20px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            margin-top: 20px;
        }

        .btn-back:hover {
            background: #e9ecef;
        }

        @media (max-width: 992px) {
            .order-detail {
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

            .order-status-bar {
                flex-direction: column;
                gap: 30px;
                align-items: flex-start;
            }

            .status-line {
                display: none;
            }

            .products-table {
                display: block;
                overflow-x: auto;
            }

            .info-grid {
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
        <!-- 页面标题 -->
        <div class="page-header">
            <div class="page-title">
                <h1>订单详情</h1>
                <p>订单号：${order.orderNumber}</p>
            </div>
        </div>

        <!-- 订单状态进度条 -->
        <div class="order-status-bar">
            <div class="status-line"></div>
            <div class="status-line-fill" id="statusLine"></div>

            <div class="status-step ${order.status == 'pending' or order.status == 'paid' or order.status == 'shipped' or order.status == 'delivered' ? 'active' : ''}">
                <div class="status-icon">📦</div>
                <div class="status-label">待发货</div>
            </div>

            <div class="status-step ${order.status == 'paid' or order.status == 'shipped' or order.status == 'delivered' ? 'active' : ''}">
                <div class="status-icon">🚚</div>
                <div class="status-label">已发货</div>
            </div>

            <div class="status-step ${order.status == 'shipped' or order.status == 'delivered' ? 'active' : ''}">
                <div class="status-icon">🏠</div>
                <div class="status-label">运输中</div>
            </div>

            <div class="status-step ${order.status == 'delivered' ? 'active' : ''}">
                <div class="status-icon">✅</div>
                <div class="status-label">已收货</div>
            </div>
        </div>

        <div class="order-detail">
            <!-- 订单信息 -->
            <div class="order-info">
                <h2 class="section-title">订单信息</h2>

                <div class="info-grid">
                    <div class="info-item">
                        <div class="info-label">订单状态</div>
                        <div class="info-value">
                            <span class="order-status ${order.statusClass}">
                                ${order.statusText}
                            </span>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">下单时间</div>
                        <div class="info-value">
                            <fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">支付方式</div>
                        <div class="info-value">${order.paymentMethod}</div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">订单金额</div>
                        <div class="info-value" style="color: #ff4757; font-weight: bold;">
                            ¥<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">收货人</div>
                        <div class="info-value">${order.contactName}</div>
                    </div>

                    <div class="info-item">
                        <div class="info-label">联系电话</div>
                        <div class="info-value">${order.contactPhone}</div>
                    </div>
                </div>

                <div class="info-item" style="margin-bottom: 20px;">
                    <div class="info-label">收货地址</div>
                    <div class="info-value">${order.shippingAddress}</div>
                </div>

                <c:if test="${not empty order.note}">
                    <div class="info-item" style="margin-bottom: 20px;">
                        <div class="info-label">订单备注</div>
                        <div class="info-value">${order.note}</div>
                    </div>
                </c:if>

                <h2 class="section-title">商品清单</h2>

                <table class="products-table">
                    <thead>
                    <tr>
                        <th>商品信息</th>
                        <th>单价</th>
                        <th>数量</th>
                        <th>小计</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>
                                <div class="product-cell">
                                    <img src="${item.productImage}" alt="${item.productName}" class="product-image">
                                    <div>
                                        <div class="product-name">${item.productName}</div>
                                        <div class="product-brand">商品ID：${item.productId}</div>
                                    </div>
                                </div>
                            </td>
                            <td class="price-cell">
                                ¥<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/>
                            </td>
                            <td class="quantity-cell">${item.quantity}</td>
                            <td class="subtotal-cell">
                                ¥<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="summary-section">
                    <div class="summary-row">
                        <span>商品金额</span>
                        <span>¥<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></span>
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
                        <span class="amount">¥<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></span>
                    </div>
                </div>

                <!-- 订单时间线 -->
                <div class="timeline">
                    <h2 class="section-title">订单跟踪</h2>

                    <div class="timeline-item">
                        <div class="timeline-content">
                            <div class="timeline-time">
                                <fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </div>
                            <div class="timeline-text">订单创建成功</div>
                        </div>
                    </div>

                    <c:if test="${order.status == 'paid' or order.status == 'shipped' or order.status == 'delivered'}">
                        <div class="timeline-item">
                            <div class="timeline-content">
                                <div class="timeline-time">
                                    <fmt:formatDate value="${order.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </div>
                                <div class="timeline-text">订单支付成功</div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.status == 'shipped' or order.status == 'delivered'}">
                        <div class="timeline-item">
                            <div class="timeline-content">
                                <div class="timeline-time">
                                    <fmt:formatDate value="${order.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </div>
                                <div class="timeline-text">商家已发货</div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.status == 'delivered'}">
                        <div class="timeline-item">
                            <div class="timeline-content">
                                <div class="timeline-time">
                                    <fmt:formatDate value="${order.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </div>
                                <div class="timeline-text">订单已送达</div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${order.status == 'cancelled'}">
                        <div class="timeline-item">
                            <div class="timeline-content">
                                <div class="timeline-time">
                                    <fmt:formatDate value="${order.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </div>
                                <div class="timeline-text">订单已取消</div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- 订单操作 -->
            <div class="order-actions">
                <h2 class="section-title">订单操作</h2>

                <div class="action-buttons">
                    <a href="order" class="btn-action">返回订单列表</a>

                    <c:if test="${order.status == 'pending'}">
                        <button class="btn-action primary" onclick="payOrder(${order.id})">立即支付</button>
                        <form action="order" method="post" style="display: inline;">
                            <input type="hidden" name="action" value="cancel">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <button type="submit" class="btn-action" onclick="return confirm('确定要取消订单吗？')">取消订单</button>
                        </form>
                    </c:if>

                    <c:if test="${order.status == 'shipped'}">
                        <button class="btn-action primary" onclick="confirmReceipt(${order.id})">确认收货</button>
                        <button class="btn-action" onclick="viewLogistics(${order.id})">查看物流</button>
                    </c:if>

                    <c:if test="${order.status == 'delivered'}">
                        <button class="btn-action" onclick="reviewOrder(${order.id})">评价商品</button>
                        <button class="btn-action" onclick="applyReturn(${order.id})">申请退换</button>
                    </c:if>

                    <c:if test="${order.status == 'cancelled'}">
                        <button class="btn-action" onclick="reorder(${order.id})">重新购买</button>
                    </c:if>

                    <button class="btn-action" onclick="contactService(${order.id})">联系客服</button>
                    <button class="btn-action" onclick="printOrder(${order.id})">打印订单</button>
                </div>

                <div style="margin-top: 30px; padding: 20px; background: #f8f9fa; border-radius: 8px;">
                    <h3 style="font-size: 14px; color: #333; margin-bottom: 10px;">温馨提示</h3>
                    <ul style="font-size: 12px; color: #666; line-height: 1.6; padding-left: 20px;">
                        <li>订单取消后无法恢复，请谨慎操作</li>
                        <li>支付成功后，订单状态将在10分钟内更新</li>
                        <li>收货后7天内可申请退换货</li>
                        <li>如有问题，请联系客服：400-123-4567</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    // 更新状态进度条
    document.addEventListener('DOMContentLoaded', function() {
        const status = '${order.status}';
        const line = document.getElementById('statusLine');

        let width = '0%';
        switch(status) {
            case 'pending': width = '0%'; break;
            case 'paid': width = '25%'; break;
            case 'shipped': width = '50%'; break;
            case 'delivered': width = '75%'; break;
            default: width = '0%';
        }

        if (line) {
            line.style.width = width;
        }
    });

    // 替换原有的 payOrder 函数
    function payOrder(orderId) {
        // 跳转到支付页面
        window.location.href = 'payment?orderId=' + orderId;
    }
    // 确认收货
    function confirmReceipt(orderId) {
        if (confirm('请确认您已收到商品')) {
            alert('确认收货功能正在开发中...');
        }
    }

    // 查看物流
    function viewLogistics(orderId) {
        alert('物流信息功能正在开发中...');
    }

    // 评价订单
    function reviewOrder(orderId) {
        alert('评价功能正在开发中...');
    }

    // 申请退换
    function applyReturn(orderId) {
        alert('退换货功能正在开发中...');
    }

    // 重新购买
    function reorder(orderId) {
        alert('重新购买功能正在开发中...');
    }

    // 联系客服
    function contactService(orderId) {
        alert('客服功能正在开发中...');
    }

    // 打印订单
    function printOrder(orderId) {
        window.print();
    }
</script>
</body>
</html>