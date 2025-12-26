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
    <title>支付结果</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: #f5f5f5;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 60px 40px;
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .result-icon {
            font-size: 80px;
            margin-bottom: 30px;
        }

        .success-icon {
            color: #2ed573;
        }

        .fail-icon {
            color: #ff4757;
        }

        .result-title {
            font-size: 32px;
            color: #333;
            margin-bottom: 20px;
        }

        .result-message {
            font-size: 16px;
            color: #666;
            margin-bottom: 30px;
            line-height: 1.6;
        }

        .order-info {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }

        .order-number {
            font-size: 18px;
            color: #333;
            margin-bottom: 10px;
        }

        .order-amount {
            font-size: 24px;
            color: #ff4757;
            font-weight: bold;
        }

        .btn-group {
            display: flex;
            gap: 20px;
            justify-content: center;
        }

        .btn {
            padding: 12px 30px;
            border-radius: 8px;
            text-decoration: none;
            font-size: 16px;
            transition: all 0.3s;
        }

        .btn-primary {
            background: #667eea;
            color: white;
        }

        .btn-primary:hover {
            background: #764ba2;
        }

        .btn-secondary {
            background: #f0f0f0;
            color: #666;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
        }
    </style>
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${param.result == 'success'}">
            <div class="result-icon success-icon">✓</div>
            <h1 class="result-title">支付成功！</h1>
            <p class="result-message">感谢您的购买，订单正在处理中，我们会尽快为您发货</p>
        </c:when>
        <c:otherwise>
            <div class="result-icon fail-icon">✗</div>
            <h1 class="result-title">支付失败</h1>
            <p class="result-message">支付过程中出现问题，请检查支付信息或更换支付方式后重试</p>
        </c:otherwise>
    </c:choose>

    <div class="order-info">
        <div class="order-number">订单号：${param.orderId}</div>
        <div class="order-amount">
            支付金额：¥<fmt:formatNumber value="${param.amount}" pattern="#,##0.00" />
        </div>
    </div>

    <div class="btn-group">
        <a href="order?action=view&id=${param.orderId}" class="btn btn-primary">查看订单</a>
        <a href="order" class="btn btn-secondary">订单列表</a>
        <a href="index.jsp" class="btn btn-secondary">继续购物</a>
    </div>
</div>
</body>
</html>