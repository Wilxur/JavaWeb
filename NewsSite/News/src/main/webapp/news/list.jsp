<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新闻网站 - 新闻列表</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            background-color: #f5f5f5;
            color: #333;
        }
        /* 顶部导航 */
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 0;
            text-align: center;
        }
        .header h1 {
            font-size: 28px;
            margin-bottom: 10px;
        }
        /* 分类导航 */
        .category-nav {
            background: white;
            padding: 15px 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .category-nav a {
            display: inline-block;
            padding: 8px 16px;
            margin-right: 10px;
            text-decoration: none;
            color: #666;
            border-radius: 20px;
            transition: all 0.3s;
        }
        .category-nav a:hover,
        .category-nav a.active {
            background: #667eea;
            color: white;
        }
        /* 主内容区 */
        .container {
            max-width: 1000px;
            margin: 20px auto;
            padding: 0 20px;
        }
        /* 新闻卡片 */
        .news-card {
            background: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 15px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            transition: transform 0.2s;
        }
        .news-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .news-card h2 {
            font-size: 18px;
            margin-bottom: 10px;
        }
        .news-card h2 a {
            color: #333;
            text-decoration: none;
        }
        .news-card h2 a:hover {
            color: #667eea;
        }
        .news-meta {
            font-size: 14px;
            color: #999;
        }
        .news-meta span {
            margin-right: 15px;
        }
        .category-tag {
            display: inline-block;
            background: #e8f0fe;
            color: #1a73e8;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        /* 广告位预留 */
        .ad-placeholder {
            background: #fff3cd;
            border: 2px dashed #ffc107;
            padding: 30px;
            text-align: center;
            margin: 20px 0;
            border-radius: 8px;
            color: #856404;
        }
        /* 空状态 */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }
    </style>
</head>
<body>
<!-- 顶部 -->
<div class="header">
    <h1>📰 新闻网站</h1>
    <p>匿名用户精准广告投放生态系统 - 新闻子系统</p>
</div>

<!-- 分类导航 -->
<div class="category-nav">
    <a href="${pageContext.request.contextPath}/news/list"
       class="${empty currentCategoryId ? 'active' : ''}">全部</a>
    <c:forEach var="cat" items="${categoryList}">
        <a href="${pageContext.request.contextPath}/news/list?categoryId=${cat.categoryId}"
           class="${currentCategoryId == cat.categoryId ? 'active' : ''}">${cat.categoryName}</a>
    </c:forEach>
</div>

<!-- 主内容 -->
<div class="container">

    <!-- 广告位预留（后期接入广告平台API） -->
    <div class="ad-placeholder">
        🎯 广告位预留区域 - 将调用广告管理平台 API 展示个性化广告
    </div>

    <!-- 新闻列表 -->
    <c:choose>
        <c:when test="${empty newsList}">
            <div class="empty-state">
                <h3>暂无新闻</h3>
                <p>该分类下还没有新闻内容</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="news" items="${newsList}">
                <div class="news-card">
                    <h2>
                        <a href="${pageContext.request.contextPath}/news/detail?id=${news.newsId}">
                                ${news.title}
                        </a>
                    </h2>
                    <div class="news-meta">
                        <span class="category-tag">${news.categoryName}</span>
                        <span>👤 ${news.author}</span>
                        <span>👁 ${news.viewCount} 次浏览</span>
                        <span>📅 ${news.publishedAt}</span>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <!-- 底部广告位 -->
    <div class="ad-placeholder">
        🎯 底部广告位 - 根据用户浏览行为推荐相关广告
    </div>
</div>
</body>
</html>