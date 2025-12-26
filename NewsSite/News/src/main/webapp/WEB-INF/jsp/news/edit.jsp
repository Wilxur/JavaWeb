<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>编辑新闻</title>

    <!-- ⚠️ 给广告系统用：当前新闻分类 -->
    <meta name="ad-category" content="${news.category}">
</head>
<body>

<h2>编辑新闻</h2>

<!-- ===== 编辑表单 ===== -->
<form method="post"
      action="${pageContext.request.contextPath}/news/edit">

    <!-- 新闻 ID（必须隐藏提交） -->
    <input type="hidden" name="id" value="${news.id}" />

    <p>
        标题：<br/>
        <input type="text"
               name="title"
               value="${news.title}"
               required
               style="width: 400px;" />
    </p>

    <p>
        内容：<br/>
        <textarea name="content"
                  rows="8"
                  cols="60"
                  required>${news.content}</textarea>
    </p>

    <!-- 提交按钮 -->
    <p>
        <button type="submit">保存修改</button>
        &nbsp;
        <a href="${pageContext.request.contextPath}/detail?id=${news.id}">
            取消
        </a>
    </p>

</form>

<hr/>

<!-- ===== 操作区（可选） ===== -->
<p>
    <a href="${pageContext.request.contextPath}/home">
        ← 返回新闻首页
    </a>
</p>

</body>
</html>