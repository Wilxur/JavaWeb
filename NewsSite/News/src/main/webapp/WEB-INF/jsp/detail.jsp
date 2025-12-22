<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>${news.title}</title>

    <!-- 给广告平台的分类说明符 -->
    <meta name="ad-category" content="${news.category}">

    <style>/* ================== 基础重置 ================== */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI",
        "PingFang SC", "Microsoft YaHei", Arial, sans-serif;
        background-color: #f4f6fb;
        color: #1f2937;
        line-height: 1.75;
    }

    /* ================== 通用容器 ================== */
    .container {
        max-width: 1100px;
        margin: 0 auto;
        padding: 28px 20px 40px;
    }

    /* ================== 顶部导航 ================== */
    .header {
        background: linear-gradient(90deg, #2563eb, #3b82f6);
        padding: 22px 0;
        margin-bottom: 36px;
        box-shadow: 0 4px 16px rgba(37, 99, 235, 0.25);
    }

    .header h1 {
        color: #ffffff;
        text-align: center;
        font-size: 26px;
        font-weight: 600;
        letter-spacing: 1px;
    }

    /* ================== 新闻列表页 ================== */
    .news-list {
        list-style: none;
    }

    .news-item {
        background-color: #ffffff;
        border-radius: 14px;
        padding: 22px 26px;
        margin-bottom: 18px;
        box-shadow: 0 10px 28px rgba(0, 0, 0, 0.06);
        transition: all 0.25s ease;
        position: relative;
    }

    .news-item::before {
        content: "";
        position: absolute;
        left: 0;
        top: 16px;
        bottom: 16px;
        width: 4px;
        background-color: #2563eb;
        border-radius: 4px;
    }

    .news-item:hover {
        transform: translateY(-4px);
        box-shadow: 0 16px 36px rgba(0, 0, 0, 0.1);
    }

    /* 分类标签 */
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

    /* 新闻标题 */
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

    /* ================== 新闻详情页 ================== */
    .news-detail-title {
        font-size: 30px;
        font-weight: 700;
        margin-bottom: 12px;
    }

    .news-detail-meta {
        font-size: 14px;
        color: #6b7280;
        margin-bottom: 22px;
    }

    /* 正文内容 */
    .news-detail-content {
        background-color: #ffffff;
        border-radius: 16px;
        padding: 36px 40px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        font-size: 16px;
    }

    /* ================== 广告区域 ================== */
    #ad-container {
        margin: 36px 0 10px;
        padding: 24px;
        border: 2px dashed #93c5fd;
        border-radius: 14px;
        text-align: center;
        background-color: #eff6ff;
        color: #2563eb;
        font-weight: 500;
        letter-spacing: 0.5px;
    }

    /* ================== 返回按钮 ================== */
    .back-link {
        display: inline-block;
        margin-top: 26px;
        color: #2563eb;
        text-decoration: none;
        font-weight: 500;
        font-size: 15px;
    }

    .back-link:hover {
        text-decoration: underline;
    }

    /* ================== 响应式（简单但够用） ================== */
    @media (max-width: 768px) {

        .header h1 {
            font-size: 22px;
        }

        .news-detail-content {
            padding: 24px;
        }

        .news-detail-title {
            font-size: 24px;
        }
    }

    /* ===== CSS 加载测试块 ===== */
    .test-css {
        width: 100%;
        height: 120px;
        background: linear-gradient(135deg, #2563eb, #60a5fa);
        color: white;
        font-size: 24px;
        font-weight: bold;
        text-align: center;
        line-height: 120px;
        border-radius: 12px;
        margin: 40px 0;
    }
    </style>
</head>
<body>

<h2>${news.title}</h2>

<p>分类：${news.category}</p>

<div>
    ${news.content}
</div>

<div class="test-css">
    CSS 正在生效
</div>

<hr/>

<!-- 广告展示容器 -->
<div id="ad-container"></div>

<!-- 广告平台 SDK -->
<script src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>

<p>
    <a href="${pageContext.request.contextPath}/home">返回首页</a>
</p>

</body>
</html>