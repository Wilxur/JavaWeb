<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${video.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <!-- ✅ 第1步：引入广告SDK -->
    <script src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>
    <!-- ✅ 第2步：SDK加载监控 -->
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
        setTimeout(onSDKLoaded, 500);
    </script>

    <style>
        /* ✅ 广告样式（保留你原有结构） */
        .ad-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: #000;
            z-index: 10;
        }
        .ad-media-host video,
        .ad-media-host img {
            width: 100%;
            height: 100%;
            object-fit: contain;
        }
    </style>
</head>
<body>

<div class="container">

    <!-- 顶部栏 -->
    <div class="topbar">
        <div class="brand">${video.title}</div>
        <div class="nav">
            <c:if test="${not empty sessionScope.user}">
                <span class="welcome">欢迎，${sessionScope.user.username}</span>
                <a class="btn" href="${pageContext.request.contextPath}/upload">上传视频</a>
                <!-- ✅ 退出登录添加确认 -->
                <a class="btn danger" href="${pageContext.request.contextPath}/logout"
                   onclick="return confirm('确定要退出登录吗？')">退出</a>
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <a class="btn primary" href="${pageContext.request.contextPath}/login">登录</a>
                <a class="btn" href="${pageContext.request.contextPath}/registerPage">注册</a>
            </c:if>
            <a class="btn" href="${pageContext.request.contextPath}/videos">返回列表</a>
        </div>
    </div>

    <!-- 播放区域 -->
    <div class="card">
        <div class="player">
            <!-- 正片视频（底层） -->
            <video id="mainVideo" class="video" data-video-id="${video.id}"
                   data-category="${video.categoryName}" controls preload="metadata"
                   src="${pageContext.request.contextPath}/media?path=${video.filePath}">
                您的浏览器不支持 video 标签
            </video>

            <!-- 广告层 -->
            <div id="adOverlay" class="ad-overlay" aria-hidden="true">
                <div class="ad-topbar">
                    <span class="ad-badge">广告</span>
                    <div class="ad-actions">
                        <span id="adCountdown" class="ad-countdown">加载中...</span>
                        <button id="adSkipBtn" class="ad-skip" type="button" disabled>跳过</button>
                    </div>
                </div>
                <!-- ✅ 广告媒体容器（SDK会渲染到这里） -->
                <div id="adMediaHost" class="ad-media-host"></div>
                <!-- ❌ 删除：点击播放广告按钮 -->
            </div>
        </div>
    </div>

</div>

<!-- ✅ 第3步：广告加载脚本（独立运行，不干扰video-player.js） -->
<script>
    // ==================== 全局定义 ====================
    var AD_API_HOST = 'http://10.100.164.17:8080/ad-platform';
    var AD_SITE_ID = 'video'; // ✅ 视频网站标识

    // ==================== 视频广告加载模块 ====================
    (function() {
        console.log('🎯 视频网站广告加载模块已启动');

        function waitForReady(callback) {
            let attempts = 0;
            const maxAttempts = 50;

            const check = () => {
                attempts++;
                const adHost = document.getElementById('adMediaHost');
                const sdkReady = typeof AdPlatformSDK !== 'undefined';

                if (adHost && sdkReady && typeof AdPlatformSDK.getUserId === 'function') {
                    console.log('✅ DOM和SDK都已准备好');
                    callback();
                } else if (attempts >= maxAttempts) {
                    console.error('❌ 等待超时');
                } else {
                    if (attempts === 1) console.log('⏳ 等待DOM和SDK...');
                    setTimeout(check, 100);
                }
            };
            check();
        }

        function loadAd() {
            const containerId = 'adMediaHost';
            console.log('📢 [' + containerId + '] 开始加载广告...');

            try {
                // 获取UID
                let uid = 'test-uid-' + Date.now();
                if (typeof AdPlatformSDK !== 'undefined' && typeof AdPlatformSDK.getUserId === 'function') {
                    uid = AdPlatformSDK.getUserId() || 'default-' + Date.now();
                }

                // 获取视频分类
                const categoryMeta = document.querySelector('meta[name="page-category"]');
                const category = categoryMeta ? categoryMeta.getAttribute('content') : 'electronics';
                console.log('📢 视频分类(中文):', category);

                // 显示加载中状态
                const container = document.getElementById(containerId);
                container.innerHTML = '<div style="color: #999; padding: 20px;">🔄 广告加载中...</div>';

                // 构建请求URL
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
                            const msg = (data && data.message) ? data.message : '暂无广告';
                            container.innerHTML = `<div style="color: #999; padding: 20px;">${msg}</div>`;
                        }
                    })
                    .catch(error => {
                        console.error('❌ 加载失败:', error);
                        container.innerHTML = '<div style="color: #e74c3c;">广告加载失败</div>';
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
            console.log('🚀 开始初始化视频网站广告...');
            console.log('✅ SDK配置完成，站点: ' + AD_SITE_ID);

            // 延迟500ms确保视频播放器先加载
            setTimeout(() => {
                console.log('⏰ 延迟完成，开始加载广告');
                loadAd();
            }, 500);
        }

        waitForReady(initAds);
    })();
</script>

<!-- 你原有的视频控制逻辑 -->
<script src="${pageContext.request.contextPath}/assets/js/video-player.js"></script>
</body>
</html>