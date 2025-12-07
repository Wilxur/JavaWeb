<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>广告管理平台 - 仪表盘</title>
    <style>
        body { font-family: Arial; margin: 0; background: #f0f2f5; }
        .header { background: #4CAF50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .nav { background: #333; padding: 0 30px; }
        .nav a { color: white; text-decoration: none; padding: 14px 20px; display: inline-block; }
        .nav a:hover { background: #111; }
        .content { padding: 30px; }
        .stats { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .stat-card h3 { margin: 0 0 10px 0; color: #666; font-size: 14px; }
        .stat-card .number { font-size: 32px; font-weight: bold; color: #4CAF50; }
    </style>
</head>
<body>
<div class="header">
    <h1>广告管理平台</h1>
    <span>欢迎, ${sessionScope.admin} | <a href="${pageContext.request.contextPath}/admin/logout" style="color:white;">退出</a></span>
</div>
<div class="nav">
    <a href="${pageContext.request.contextPath}/admin/dashboard">仪表盘</a>
    <a href="${pageContext.request.contextPath}/admin/ad/list">广告管理</a>
    <a href="${pageContext.request.contextPath}/admin/ad/add">添加广告</a>
</div>
<div class="content">
    <h2>数据概览</h2>
    <div class="stats">
        <div class="stat-card">
            <h3>总广告数</h3>
            <div class="number"><%= new com.adplatform.service.AdService().getAllAds().size() %></div>
        </div>
        <div class="stat-card">
            <h3>今日展示</h3>
            <div class="number">0</div>
        </div>
        <div class="stat-card">
            <h3>今日点击</h3>
            <div class="number">0</div>
        </div>
    </div>
</div>
</body>
</html>