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
    <title>支付订单</title>
    <style>
        body { font-family: 'Microsoft YaHei'; background: #f5f5f5; }
        .container { max-width: 800px; margin: 40px auto; background: white; padding: 40px; border-radius: 10px; }
        .order-info { margin-bottom: 30px; padding: 20px; background: #f8f9fa; border-radius: 8px; }
        .payment-methods { display: flex; gap: 20px; margin: 20px 0; }
        .method { flex: 1; padding: 20px; border: 2px solid #e1e1e1; border-radius: 8px; cursor: pointer; text-align: center; }
        .method:hover, .method.selected { border-color: #667eea; background: #f0f5ff; }
        .btn-pay { width: 100%; padding: 15px; background: #667eea; color: white; border: none; border-radius: 8px; font-size: 18px; cursor: pointer; }
        .btn-pay:hover { background: #764ba2; }
    </style>
</head>
<body>
<div class="container">
    <h2>支付订单</h2>

    <div class="order-info">
        <h3>订单号：${order.orderNumber}</h3>
        <p>应付金额：<span style="color: #ff4757; font-size: 24px; font-weight: bold;">¥<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></span></p>
    </div>

    <h3>选择支付方式</h3>
    <div class="payment-methods">
        <div class="method selected" data-method="alipay">
            <div style="font-size: 40px;">💰</div>
            <div>支付宝</div>
        </div>
        <div class="method" data-method="wechat">
            <div style="font-size: 40px;">💳</div>
            <div>微信支付</div>
        </div>
        <div class="method" data-method="card">
            <div style="font-size: 40px;">🏦</div>
            <div>银行卡</div>
        </div>
    </div>

    <form action="payment" method="post" id="paymentForm">
        <input type="hidden" name="orderId" value="${order.id}">
        <input type="hidden" name="paymentMethod" id="selectedMethod" value="alipay">
    </form>

    <button class="btn-pay" onclick="submitPayment()">确认支付 ¥<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></button>
</div>

<script>
    // 支付方式选择
    document.querySelectorAll('.method').forEach(m => {
        m.addEventListener('click', function() {
            document.querySelectorAll('.method').forEach(x => x.classList.remove('selected'));
            this.classList.add('selected');
            document.getElementById('selectedMethod').value = this.dataset.method;
        });
    });

    // 提交支付
    function submitPayment() {
        if(confirm('确认支付 ¥${order.totalAmount} 元？')) {
            document.getElementById('paymentForm').submit();
        }
    }
</script>
</body>
</html>