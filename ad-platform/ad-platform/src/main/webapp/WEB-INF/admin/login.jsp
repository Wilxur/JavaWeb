<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>广告管理平台 - 登录</title>
    <style>
        body { font-family: Arial; background: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-box { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 300px; }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        input { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #45a049; }
        .error { color: red; text-align: center; margin-top: 10px; }
    </style>
</head>
<body>
<div class="login-box">
    <h2>广告管理平台</h2>
    <form action="${pageContext.request.contextPath}/admin/login" method="post">
        <input type="text" name="username" placeholder="用户名" required value="admin">
        <input type="password" name="password" placeholder="密码" required value="123456">
        <button type="submit">登录</button>
    </form>
    <% if (request.getParameter("error") != null) { %>
    <div class="error">用户名或密码错误</div>
    <% } %>
</div>
</body>
</html>