<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${news.title}</title>

    <!-- 给广告平台的分类说明符 -->
    <meta name="ad-category" content="${news.category}">

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
            line-height: 1.8;
        }

        a {
            color: #2563eb;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        /* ===== 顶部 ===== */
        .header {
            background: linear-gradient(90deg, #2563eb, #3b82f6);
            padding: 20px 0;
            color: white;
            box-shadow: 0 4px 16px rgba(37, 99, 235, 0.25);
        }

        .header .container {
            max-width: 900px;
            margin: 0 auto;
            padding: 0 20px;
            font-size: 18px;
            font-weight: 600;
        }

        /* ===== 主体 ===== */
        .container {
            max-width: 900px;
            margin: 32px auto;
            padding: 0 20px;
        }

        /* ===== 新闻卡片 ===== */
        .article-card {
            background: white;
            border-radius: 16px;
            padding: 36px 40px;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        }

        .article-title {
            font-size: 30px;
            font-weight: 700;
            margin-bottom: 14px;
        }

        .article-meta {
            font-size: 14px;
            color: #6b7280;
            margin-bottom: 26px;
        }

        .article-category {
            display: inline-block;
            background-color: #e0e7ff;
            color: #2563eb;
            padding: 4px 10px;
            border-radius: 999px;
            font-size: 13px;
            margin-left: 8px;
        }

        .article-content {
            font-size: 16px;
            white-space: pre-line;
        }

        /* ===== 删除区 ===== */
        .article-actions {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px dashed #e5e7eb;
        }

        .delete-btn {
            background: none;
            border: none;
            color: #dc2626;
            cursor: pointer;
            font-size: 14px;
        }

        .delete-btn:hover {
            text-decoration: underline;
        }

        /* ===== 广告区 ===== */
        .ad-box {
            margin: 36px auto 10px;
            padding: 24px;
            border: 2px dashed #93c5fd;
            border-radius: 14px;
            text-align: center;
            background-color: #eff6ff;
            color: #2563eb;
            font-weight: 500;
        }

        /* ===== 返回 ===== */
        .back-link {
            margin-top: 26px;
            display: inline-block;
            font-size: 14px;
        }

        /* ===== 响应式 ===== */
        @media (max-width: 768px) {
            .article-card {
                padding: 24px;
            }

            .article-title {
                font-size: 24px;
            }
        }
    </style>
</head>
<body>

<!-- 顶部 -->
<div class="header">
    <div class="container">
        新闻详情
    </div>
</div>

<!-- 主体 -->
<div class="container">

    <div class="article-card">

        <div class="article-title">
            ${news.title}
        </div>

        <div class="article-meta">
            分类：
            <span class="article-category">
                ${news.category}
            </span>
        </div>

        <div class="article-content">
            ${news.content}
        </div>

        <!-- 删除操作 -->
        <c:if test="${not empty sessionScope.loginUser}">
            <div class="article-actions">
                <form method="post"
                      action="${pageContext.request.contextPath}/news/delete"
                      onsubmit="return confirm('确定要删除这条新闻吗？');">

                    <input type="hidden" name="id" value="${news.id}" />

                    <button class="delete-btn" type="submit">
                        删除新闻
                    </button>
                </form>
            </div>
        </c:if>

    </div>

    <!-- 广告 -->
    <div id="ad-container" class="ad-box">
        广告加载中…
    </div>

    <!-- 广告平台 SDK -->
    <script src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>

    <a class="back-link" href="${pageContext.request.contextPath}/home">
        ← 返回首页
    </a>

</div>

</body>
</html>