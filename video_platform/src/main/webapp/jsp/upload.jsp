<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>上传视频</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<div class="container">
    <div class="card">
        <h2 class="h-title">上传视频</h2>

        <form class="form"
              action="${pageContext.request.contextPath}/uploadVideo"
              method="post"
              enctype="multipart/form-data">

            <div class="field">
                <label class="label">视频标题</label>
                <input class="input" type="text" name="title" required>
            </div>

            <div class="field">
                <label class="label">视频分类</label>
                <select name="categoryId" required>
                    <c:forEach items="${categories}" var="c">
                        <option value="${c.id}">${c.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="field">
                <label class="label">选择视频文件</label>
                <input class="input" type="file" name="videoFile" accept="video/*" required>
            </div>

            <div class="row space">
                <button class="btn primary" type="submit">上传</button>
                <a class="btn" href="${pageContext.request.contextPath}/videos">返回列表</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
