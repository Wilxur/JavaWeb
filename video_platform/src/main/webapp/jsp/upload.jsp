<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>上传视频</title>
</head>
<body>

<h2>上传视频</h2>

<form action="uploadVideo" method="post" enctype="multipart/form-data">
    <p>
        视频标题：
        <input type="text" name="title" required />
    </p>

    <p>
        视频文件：
        <input type="file" name="videoFile" accept="video/*" required />
    </p>

    <p>
        <button type="submit">上传</button>
        <a href="videos">返回列表</a>
    </p>
</form>

</body>
</html>
