<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${news.title}</title>
    <meta name="page-category" content="${news.category}">
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

        /* ==================== 广告样式添加 ==================== */
        .ad-container {
            margin: 40px auto 20px;
            padding: 24px;
            border: 2px dashed #93c5fd;
            border-radius: 14px;
            text-align: center;
            background-color: #eff6ff;
            color: #2563eb;
            font-weight: 500;
            max-width: 900px;
        }

        .ad-container h3 {
            font-size: 16px;
            margin-bottom: 15px;
            color: #2563eb;
        }

        .ad-placeholder {
            min-height: 100px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* 广告内容渲染后的样式 */
        .ad-image {
            max-width: 100% !important;
            height: auto !important;
            max-height: 180px !important;
            border-radius: 8px;
            margin: 0 auto;
            display: block;
        }

        .ad-text {
            padding: 15px;
            background: white;
            border-radius: 8px;
            line-height: 1.6;
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
    <script src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>
    <script>
        console.log('🎯 SDK加载监控脚本已初始化');
        window.sdkLoaded = false;
        window.sdkLoadCallbacks = [];

        function onSDKLoaded() {
            console.log('✅ SDK脚本标签加载完成');
            window.sdkLoaded = true;
            window.sdkLoadCallbacks.forEach(callback => {
                try {
                    callback();
                } catch (e) {
                    console.error('❌ SDK回调执行失败:', e);
                }
            });
            window.sdkLoadCallbacks = [];
        }

        // 延迟500ms确保SDK全局变量可用
        setTimeout(onSDKLoaded, 500);
    </script>
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
    <div class="ad-container">
        <h3>💡 相关推荐</h3>
        <div id="ad-news-bottom" class="ad-placeholder">
            广告加载中...
        </div>
    </div>
    <a class="back-link" href="${pageContext.request.contextPath}/home">
        ← 返回首页
    </a>

</div>
<script>
    // ==================== 全局定义 ====================
    var AD_API_HOST = 'http://10.100.164.17:8080/ad-platform';
    var AD_SITE_ID = 'news';

    // ==================== 广告加载模块 ====================
    (function() {
        console.log('🎯 新闻站广告加载模块已启动');

        function waitForReady(callback) {
            let attempts = 0;
            const maxAttempts = 50;

            const check = () => {
                attempts++;
                const adContainer = document.getElementById('ad-news-bottom');
                const sdkReady = typeof AdPlatformSDK !== 'undefined';

                if (adContainer && sdkReady && typeof AdPlatformSDK.getUserId === 'function') {
                    console.log('✅ DOM和SDK都已准备好');
                    callback();
                } else if (attempts >= maxAttempts) {
                    console.error('❌ 超时');
                } else {
                    if (attempts === 1) console.log('⏳ 等待DOM和SDK...');
                    setTimeout(check, 100);
                }
            };
            check();
        }

        function loadAd(containerId) {
            console.log('📢 [' + containerId + '] 开始加载广告...');

            try {
                // 获取UID
                let uid = 'test-uid-' + Date.now();
                if (typeof AdPlatformSDK !== 'undefined' && typeof AdPlatformSDK.getUserId === 'function') {
                    uid = AdPlatformSDK.getUserId() || 'default-' + Date.now();
                }
                console.log('📢 UID:', uid);

                // ✅ 关键：直接传递中文分类，让后端API处理映射
                const categoryMeta = document.querySelector('meta[name="page-category"]');
                const category = categoryMeta ? categoryMeta.getAttribute('content') : 'electronics';
                console.log('📢 新闻分类(中文):', category); // 输出: 社会 / 体育 / 科技

                // 显示加载中状态
                const container = document.getElementById(containerId);
                container.innerHTML = '<div style="color: #999;">🔄 正在加载相关推荐...</div>';

                // 构建请求URL（传递中文分类）
                const apiUrl = AD_API_HOST + '/api/ad/get?uid=' + encodeURIComponent(uid) +
                    '&category=' + encodeURIComponent(category) +
                    '&site=' + encodeURIComponent(AD_SITE_ID);
                console.log('📢 请求URL:', apiUrl);

                // 调用API
                fetch(apiUrl)
                    .then(response => {
                        console.log('📢 HTTP状态码:', response.status);
                        if (!response.ok) throw new Error('HTTP ' + response.status);
                        return response.json();
                    })
                    .then(data => {
                        console.log('✅ API返回:', data);
                        if (data && data.success === true && data.ad) {
                            console.log('🎯 广告对象:', data.ad);
                            container.innerHTML = '';
                            AdPlatformSDK.renderAd(containerId, data.ad);
                            console.log('✅ 渲染成功！');
                            reportAdImpression(data.ad.id, uid, category);
                        } else {
                            const msg = (data && data.message) ? data.message : '暂无推荐';
                            container.innerHTML = '<div style="color: #999;">' + msg + '</div>';
                        }
                    })
                    .catch(error => {
                        console.error('❌ 加载失败:', error);
                        container.innerHTML = '<div style="color: #e74c3c;">推荐加载失败</div>';
                    });
            } catch (e) {
                console.error('❌ 初始化异常:', e);
            }
        }

        function reportAdImpression(adId, uid, category) {
            try {
                const url = AD_API_HOST + '/api/track/impression?uid=' + encodeURIComponent(uid) +
                    '&adId=' + encodeURIComponent(adId) + '&site=' + encodeURIComponent(AD_SITE_ID) +
                    '&category=' + encodeURIComponent(category);
                console.log('📢 上报展示:', url);
                fetch(url)
                    .then(() => console.log('📢 广告 ' + adId + ' 上报成功'))
                    .catch(err => console.error('❌ 上报失败:', err));
            } catch (e) {
                console.error('❌ 上报异常:', e);
            }
        }

        function initAds() {
            console.log('🚀 开始初始化新闻站广告...');
            console.log('✅ SDK配置完成，站点: ' + AD_SITE_ID);

            setTimeout(() => {
                console.log('⏰ 延迟完成，开始加载广告');
                loadAd('ad-news-bottom');
            }, 500);
        }

        waitForReady(initAds);
    })();
</script>
</body>
</html>