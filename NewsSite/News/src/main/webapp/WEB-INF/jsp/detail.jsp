<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${news.title}</title>

    <!-- 给广告平台的分类说明符 -->
    <meta name="ad-category" content="${news.category}">
</head>
<body>

<h2>${news.title}</h2>

<p>分类：${news.category}</p>

<hr/>

<div>
    ${news.content}
</div>

<hr/>

<!-- ===== 删除新闻区块（仅登录用户可见） ===== -->
<c:if test="${not empty sessionScope.loginUser}">
    <form method="post"
          action="${pageContext.request.contextPath}/news/delete"
          onsubmit="return confirm('确定要删除这条新闻吗？');">

        <input type="hidden" name="id" value="${news.id}" />

        <button type="submit" style="color:red;">
            删除新闻
        </button>
    </form>
</c:if>

<hr/>

<!-- ===== 广告展示容器 ===== -->
<div id="ad-container">
    广告加载中…
</div>

<!-- 广告平台 SDK -->
<script src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>

<hr/>

<p>
    <a href="${pageContext.request.contextPath}/home">返回首页</a>
</p>

</body>
</html>