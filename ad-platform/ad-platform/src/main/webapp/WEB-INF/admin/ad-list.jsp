<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.adplatform.model.Ad, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>广告管理</title>
    <style>
        body { font-family: Arial; margin: 0; background: #f0f2f5; }
        .header { background: #4CAF50; color: white; padding: 15px 30px; }
        .nav { background: #333; padding: 0 30px; }
        .nav a { color: white; text-decoration: none; padding: 14px 20px; display: inline-block; }
        .content { padding: 30px; }
        table { width: 100%; background: white; border-collapse: collapse; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #4CAF50; color: white; }
        tr:hover { background: #f5f5f5; }
        .btn { background: #4CAF50; color: white; padding: 8px 16px; text-decoration: none; border-radius: 4px; }
        .success { background: #d4edda; color: #155724; padding: 10px; margin-bottom: 20px; border-radius: 4px; }
    </style>
</head>
<body>
<div class="header">
    <h1>广告管理平台</h1>
</div>
<div class="nav">
    <a href="${pageContext.request.contextPath}/admin/dashboard">仪表盘</a>
    <a href="${pageContext.request.contextPath}/admin/ad/list">广告管理</a>
    <a href="${pageContext.request.contextPath}/admin/ad/add">添加广告</a>
</div>
<div class="content">
    <h2>广告列表</h2>
    <a href="${pageContext.request.contextPath}/admin/ad/add" class="btn">+ 添加广告</a>

    <% if (request.getParameter("success") != null) { %>
    <div class="success">操作成功！</div>
    <% } %>

    <table style="margin-top:20px;">
        <tr>
            <th>ID</th>
            <th>标题</th>
            <th>类型</th>
            <th>分类</th>
            <th>状态</th>
            <th>创建时间</th>
        </tr>
        <%
            List<Ad> ads = (List<Ad>) request.getAttribute("ads");
            if (ads != null) {
                for (Ad ad : ads) {
        %>
        <tr>
            <td><%= ad.getId() %></td>
            <td><%= ad.getTitle() %></td>
            <td><%= ad.getAdType() %></td>
            <td><%= ad.getCategory() %></td>
            <td><%= ad.getStatus() == 1 ? "激活" : "禁用" %></td>
            <td><%= ad.getCreatedAt() %></td>
        </tr>
        <% } } %>
    </table>
</div>
</body>
</html>