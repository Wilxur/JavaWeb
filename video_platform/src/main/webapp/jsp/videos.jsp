<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>视频列表</title>
</head>
<body>
<h2>视频列表</h2>

<p><a href="index">返回首页</a> | <a href="upload">上传视频</a></p>

<ul>
    <c:forEach items="${videos}" var="v">
        <li>
                ${v.title}（${v.category}）
            <a href="video?id=${v.id}">播放</a>
        </li>
    </c:forEach>
</ul>

</body>
</html>
