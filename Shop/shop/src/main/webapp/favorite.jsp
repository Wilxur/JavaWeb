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
    <title>我的收藏 - 购物网站</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Microsoft YaHei', Arial, sans-serif; background: #f5f5f5; }
        .header { background: white; box-shadow: 0 2px 10px rgba(0,0,0,0.1); padding: 15px 0; position: sticky; top: 0; z-index: 100; }
        .container { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
        .header-content { display: flex; justify-content: space-between; align-items: center; }
        .logo { font-size: 24px; font-weight: bold; color: #667eea; text-decoration: none; }
        .user-info { display: flex; align-items: center; gap: 20px; }
        .welcome { color: #666; }
        .username { color: #667eea; font-weight: bold; }
        .nav-links { display: flex; gap: 20px; }
        .nav-link { color: #666; text-decoration: none; padding: 5px 10px; border-radius: 5px; transition: all .3s; }
        .nav-link:hover { color: #667eea; background: #f0f0f0; }
        .nav-link.active { color: #667eea; background: #f0f0f0; }
        .btn-logout { background: #e74c3c; color: white; border: none; padding: 8px 16px; border-radius: 5px; cursor: pointer; text-decoration: none; font-size: 14px; }
        .btn-logout:hover { background: #c0392b; }
        .main-content { padding: 40px 0; }
        .page-title { text-align: center; margin-bottom: 40px; color: #333; }
        .page-title h1 { font-size: 36px; margin-bottom: 10px; }
        .page-title p { color: #666; font-size: 16px; }
        .favorite-empty { text-align: center; padding: 60px 20px; color: #666; }
        .favorite-empty h3 { font-size: 24px; margin-bottom: 15px; color: #333; }
        .btn-shopping { background: #667eea; color: white; border: none; padding: 12px 30px; border-radius: 8px; cursor: pointer; font-size: 16px; text-decoration: none; display: inline-block; }
        .btn-shopping:hover { background: #764ba2; }
        .favorite-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 30px; margin-top: 30px; }
        .favorite-card { background: white; border-radius: 15px; overflow: hidden; box-shadow: 0 5px 20px rgba(0,0,0,0.1); transition: transform .3s; display: flex; flex-direction: column; height: 100%; }
        .favorite-card:hover { transform: translateY(-5px); }
        .favorite-image { width: 100%; height: 180px; object-fit: cover; }
        .favorite-content { padding: 20px; flex: 1; display: flex; flex-direction: column; }
        .favorite-brand { color: #666; font-size: 12px; margin-bottom: 5px; }
        .favorite-name { font-size: 16px; color: #333; margin-bottom: 10px; line-height: 1.4; height: 45px; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; }
        .favorite-price { font-size: 20px; font-weight: bold; color: #ff4757; margin-bottom: 15px; }
        .favorite-actions { display: flex; gap: 10px; margin-top: auto; }
        .btn-add-cart { flex: 1; background: #667eea; color: white; border: none; padding: 10px; border-radius: 5px; cursor: pointer; transition: background .3s; }
        .btn-add-cart:hover { background: #764ba2; }
        .btn-remove { background: #f8f9fa; color: #666; border: 1px solid #e1e1e1; padding: 10px; border-radius: 5px; cursor: pointer; transition: all .3s; }
        .btn-remove:hover { background: #ff4757; color: white; border-color: #ff4757; }
        @media (max-width: 768px) { .header-content { flex-direction: column; gap: 15px; } .nav-links { flex-wrap: wrap; justify-content: center; } .user-info { flex-direction: column; gap: 10px; } }
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
                <a href="order" class="nav-link">我的订单</a>
                <a href="favorite" class="nav-link active">收藏夹</a>
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
            <h1>我的收藏</h1>
            <p>您收藏的商品会在这里显示</p>
        </div>

        <c:choose>
            <c:when test="${empty favorites or favorites.size() == 0}">
                <div class="favorite-empty">
                    <h3>暂无收藏</h3>
                    <p>快去挑选心仪的商品吧！</p>
                    <a href="index.jsp" class="btn-shopping">去逛逛</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="favorite-grid">
                    <c:forEach var="item" items="${favorites}">
                        <div class="favorite-card">
                            <img src="${item.product.imageUrl}" alt="${item.product.name}" class="favorite-image">
                            <div class="favorite-content">
                                <div class="favorite-brand">${item.product.brand}</div>
                                <h3 class="favorite-name">${item.product.name}</h3>
                                <div class="favorite-price">
                                    ¥<fmt:formatNumber value="${item.product.price}" pattern="#,##0.00"/>
                                </div>
                                <div class="favorite-actions">
                                    <button class="btn-add-cart" onclick="addToCart(${item.product.id})">加入购物车</button>
                                    <button class="btn-remove" onclick="removeFavorite(${item.product.id}, this)">移除</button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<script>
    /* 加入购物车（沿用商品页逻辑） */
    function addToCart(productId) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = 'cart';
        form.style.display = 'none';
        ['action','productId','quantity','category'].forEach(key => {
            const i = document.createElement('input');
            i.type = 'hidden';
            i.name  = key;
            i.value = key==='action'?'add': key==='quantity'?'1': productId;
            if(key==='category') i.value='electronics';
            form.appendChild(i);
        });
        document.body.appendChild(form);
        form.submit();
    }

    /* 移除收藏（AJAX） */
    function removeFavorite(productId, btn) {
        if(!confirm('确定移除收藏？')) return;
        fetch('favorite',{
            method:'POST',
            headers:{'Content-Type':'application/x-www-form-urlencoded'},
            body:'action=remove&productId='+productId
        }).then(res => res.json())
            .then(json => {
                if(json.success){
                    btn.closest('.favorite-card').remove();
                    if(document.querySelectorAll('.favorite-card').length === 0){
                        location.reload(); // 刷新空状态
                    }
                }
            });
    }
</script>
</body>
</html>