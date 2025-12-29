<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>新闻首页</title>

    <style>
        /* ===== 基础 ===== */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #f4f6fb;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI",
            "PingFang SC", "Microsoft YaHei", Arial, sans-serif;
            color: #1f2937;
        }

        /* ===== 顶部栏 ===== */
        .top-bar {
            background: linear-gradient(90deg, #2563eb, #3b82f6);
            padding: 18px 0;
            color: white;
            box-shadow: 0 4px 16px rgba(37, 99, 235, 0.25);
        }

        .top-bar .container {
            max-width: 1100px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .top-bar a {
            color: white;
            text-decoration: none;
            margin-left: 14px;
            font-size: 14px;
        }

        .top-bar a:hover {
            text-decoration: underline;
        }

        /* ===== 页面主体 ===== */
        .container {
            max-width: 1100px;
            margin: 32px auto;
            padding: 0 20px;
        }

        h2 {
            margin-bottom: 22px;
            font-size: 24px;
        }

        /* ===== 新闻列表 ===== */
        .news-list {
            list-style: none;
        }

        .news-card {
            background: white;
            border-radius: 14px;
            padding: 20px 26px;
            margin-bottom: 18px;
            box-shadow: 0 10px 28px rgba(0, 0, 0, 0.06);
            transition: transform 0.25s ease, box-shadow 0.25s ease;
            position: relative;
        }

        .news-card::before {
            content: "";
            position: absolute;
            left: 0;
            top: 18px;
            bottom: 18px;
            width: 4px;
            background-color: #2563eb;
            border-radius: 4px;
        }

        .news-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 16px 36px rgba(0, 0, 0, 0.1);
        }

        .news-category {
            display: inline-block;
            font-size: 13px;
            color: #2563eb;
            background-color: #e0e7ff;
            padding: 5px 12px;
            border-radius: 999px;
            margin-bottom: 10px;
            font-weight: 500;
        }

        .news-title {
            display: block;
            font-size: 19px;
            font-weight: 600;
            color: #111827;
            text-decoration: none;
        }

        .news-title:hover {
            color: #2563eb;
        }

        .news-actions {
            margin-top: 10px;
            font-size: 13px;
        }

        .news-actions a {
            color: #2563eb;
            text-decoration: none;
            margin-right: 12px;
        }

        .news-actions a:hover {
            text-decoration: underline;
        }
    </style>


</head>
<body>

<!-- ===== 顶部导航栏 ===== -->
<div class="top-bar">
    <div class="container">
        <!-- ===== 分类导航栏 ===== -->
        <div style="
    margin-bottom: 16px;
    padding: 12px 0;
    border-bottom: 1px solid #ddd;
">

            <strong>分类：</strong>

            <a href="${pageContext.request.contextPath}/home"
               style="margin-right:10px;">
                全部
            </a>

            <a href="${pageContext.request.contextPath}/home?categoryId=1"
               style="margin-right:10px;">
                科技
            </a>

            <a href="${pageContext.request.contextPath}/home?categoryId=2"
               style="margin-right:10px;">
                财经
            </a>

            <a href="${pageContext.request.contextPath}/home?categoryId=3"
               style="margin-right:10px;">
                体育
            </a>

            <a href="${pageContext.request.contextPath}/home?categoryId=4"
               style="margin-right:10px;">
                娱乐
            </a>

            <a href="${pageContext.request.contextPath}/home?categoryId=5"
               style="margin-right:10px;">
                社会
            </a>

            <a href="${pageContext.request.contextPath}/home?categoryId=6">
                教育
            </a>
        </div>
        <div>
            <strong>NewsSite</strong>
        </div>

        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.loginUser}">
                    欢迎，
                    <strong>${sessionScope.loginUser.username}</strong>
                    |
                    <a href="${pageContext.request.contextPath}/news/add">
                        发布新闻
                    </a>
                    |
                    <a href="${pageContext.request.contextPath}/logout">
                        退出
                    </a>
                </c:when>

                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">
                        登录
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<!-- ===== 主体内容 ===== -->
<div class="container">

    <h2>最新新闻</h2>

    <ul class="news-list">
        <c:forEach var="news" items="${newsList}">
            <li class="news-card">

                <span class="news-category">
                        ${news.category}
                </span>

                <a class="news-title"
                   href="${pageContext.request.contextPath}/detail?id=${news.id}">
                        ${news.title}
                </a>

                <!-- 编辑入口 -->
                <c:if test="${not empty sessionScope.loginUser}">
                    <div class="news-actions">
                        <a href="${pageContext.request.contextPath}/news/edit?id=${news.id}">
                            编辑
                        </a>
                    </div>
                </c:if>

            </li>
        </c:forEach>
    </ul>

</div>
</body>
</html>