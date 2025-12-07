<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加广告</title>
    <style>
        body { font-family: Arial; margin: 0; background: #f0f2f5; }
        .header { background: #4CAF50; color: white; padding: 15px 30px; }
        .nav { background: #333; padding: 0 30px; }
        .nav a { color: white; text-decoration: none; padding: 14px 20px; display: inline-block; }
        .content { padding: 30px; max-width: 800px; margin: 0 auto; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        textarea { height: 100px; }
        button { background: #4CAF50; color: white; padding: 12px 30px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background: #45a049; }
        .error { color: red; margin-top: 10px; }
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
    <h2>添加新广告</h2>
    <form action="${pageContext.request.contextPath}/admin/ad/add" method="post">
        <div class="form-group">
            <label>广告标题</label>
            <input type="text" name="title" required placeholder="如：iPhone 15 限时优惠">
        </div>
        <div class="form-group">
            <label>广告类型</label>
            <select name="adType" required>
                <option value="text">文字</option>
                <option value="image" selected>图片</option>
                <option value="video">视频</option>
            </select>
        </div>
        <div class="form-group">
            <label>内容（文字内容或文件URL）</label>
            <textarea name="content" required placeholder="图片/视频URL，如：https://picsum.photos/300/250"></textarea>
        </div>
        <div class="form-group">
            <label>分类</label>
            <select name="category" required>
                <option value="electronics">电子产品</option>
                <option value="clothing">服装</option>
                <option value="food">食品</option>
                <option value="sports">运动</option>
                <option value="beauty">美妆</option>
                <option value="books">图书</option>
            </select>
        </div>
        <div class="form-group">
            <label>目标链接</label>
            <input type="url" name="targetUrl" required placeholder="https://...">
        </div>
        <button type="submit">发布广告</button>
    </form>

    <% if (request.getParameter("error") != null) { %>
    <div class="error">添加失败，请检查输入</div>
    <% } %>
</div>
</body>
</html>