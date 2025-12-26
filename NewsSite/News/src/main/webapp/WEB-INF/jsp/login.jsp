<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
</head>
<body>

<h2>用户登录</h2>

<form method="post" action="${pageContext.request.contextPath}/login">
    <div>
        用户名：
        <input type="text" name="username" required />
    </div>
    <div>
        密码：
        <input type="password" name="password" required />
    </div>
    <div>
        <button type="submit">登录</button>
    </div>
</form>

<!-- 登录失败提示 -->
<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<hr/>

<!-- ⭐ 关键：注册入口 -->
<p>
    还没有账号？
    <a href="${pageContext.request.contextPath}/register">
        去注册
    </a>
</p>

</body>
</html>