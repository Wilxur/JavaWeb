<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${news.title} - 新闻网站</title>
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
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
        }
        .header a {
            color: white;
            text-decoration: none;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 0 20px;
        }
        .article {
            background: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .article h1 {
            font-size: 24px;
            margin-bottom: 15px;
            line-height: 1.4;
        }
        .article-meta {
            font-size: 14px;
            color: #999;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
            margin-bottom: 20px;
        }
        .article-meta span {
            margin-right: 20px;
        }
        .category-tag {
            display: inline-block;
            background: #e8f0fe;
            color: #1a73e8;
            padding: 3px 10px;
            border-radius: 4px;
            font-size: 12px;
        }
        .article-content {
            font-size: 16px;
            line-height: 1.8;
            color: #333;
            white-space: pre-wrap;
        }
        .ad-placeholder {
            background: #fff3cd;
            border: 2px dashed #ffc107;
            padding: 20px;
            text-align: center;
            margin: 20px 0;
            border-radius: 8px;
            color: #856404;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #667eea;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<!-- 顶部 -->
<div class="header">
    <a href="${pageContext.request.contextPath}/news/list">📰 新闻网站</a>
</div>

<div class="container">

    <!-- 顶部广告位 -->
    <div class="ad-placeholder">
        🎯 文章顶部广告位 - 根据文章分类「${news.categoryName}」推荐相关广告
    </div>

    <!-- 文章内容 -->
    <div class="article">
        <h1>${news.title}</h1>
        <div class="article-meta">
            <span class="category-tag">${news.categoryName}</span>
            <span>👤 ${news.author}</span>
            <span>👁 ${news.viewCount} 次浏览</span>
            <span>📅 ${news.publishedAt}</span>
        </div>
        <div class="article-content">
            ${news.content}
        </div>

        <a href="${pageContext.request.contextPath}/news/list" class="back-link">← 返回新闻列表</a>
    </div>

    <!-- 底部广告位 -->
    <div class="ad-placeholder">
        🎯 文章底部广告位 - 基于用户阅读行为推荐个性化广告
    </div>
</div>

<!-- 用户行为上报（隐藏脚本，Day7实现） -->
<script>
    // 上报用户阅读行为
    var newsId = '${news.newsId}';
    var categoryId = '${news.categoryId}';
    var categoryName = '${news.categoryName}';
    console.log('用户正在阅读：', newsId, categoryName);
    // Day7 会添加真正的上报逻辑
</script>
</body>
</html>
```

---

## 🔧 关于你代码中的警告

从截图看到有警告：
```
字段 'newsService' 可能为 'final'