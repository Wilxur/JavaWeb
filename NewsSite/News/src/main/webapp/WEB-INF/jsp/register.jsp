<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
</head>
<body>

<h2>用户注册</h2>

<form method="post" action="${pageContext.request.contextPath}/register">
    <div>
        用户名：
        <input type="text" name="username" required />
    </div>
    <div>
        密码：
        <input type="password" name="password" required />
    </div>
    <div>
        <button type="submit">注册</button>
    </div>
</form>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<p>
    <a href="${pageContext.request.contextPath}/login">
        返回登录
    </a>
</p>

</body>
</html>