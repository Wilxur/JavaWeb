/**
 * 广告平台集成脚本 - 多服务器部署版
 * 各站点必须在引入前配置 window.AD_CONFIG
 */
(function(window, document) {
    'use strict';

    // ========== 配置检查 ==========
    if (!window.AD_CONFIG) {
        console.error('【广告平台】未配置AD_CONFIG，请在引入脚本前设置');
        return;
    }

    const CONFIG = {
        API_BASE: window.AD_CONFIG.API_BASE,  // 必须配置
        SITE_ID: window.AD_CONFIG.SITE_ID,    // 必须配置：shopping/video/news
        UID_KEY: 'ad_platform_uid'
    };

    // ========== UID管理 ==========
    function getUid() {
        // 1. 从URL参数获取（跨站跳转）
        const urlParams = new URLSearchParams(window.location.search);
        let uid = urlParams.get('ad_uid');

        // 2. 从LocalStorage获取
        if (!uid) {
            uid = localStorage.getItem(CONFIG.UID_KEY);
        }

        // 3. 生成新UID
        if (!uid) {
            uid = 'uid_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
            localStorage.setItem(CONFIG.UID_KEY, uid);
            // 静默注册到广告平台
            fetch(`${CONFIG.API_BASE}/user/init?uid=${uid}`).catch(() => {});
        }

        return uid;
    }

    // 在站内所有链接附加UID参数
    function appendUidToLinks() {
        const uid = getUid();
        document.querySelectorAll('a[href]').forEach(link => {
            const href = link.getAttribute('href');
            if (href && href.startsWith('http') && !href.includes(CONFIG.API_BASE)) {
                const separator = href.includes('?') ? '&' : '?';
                link.href = href + separator + 'ad_uid=' + uid;
            }
        });
    }

    // ========== 广告加载 ==========
    window.loadAd = function(adType, containerId, pageCategory, callback) {
        const uid = getUid();
        const container = document.getElementById(containerId);

        if (!container) {
            console.error('【广告平台】容器不存在:', containerId);
            return;
        }

        container.innerHTML = '<div style="text-align:center;padding:20px;color:#999;">广告加载中...</div>';

        const apiUrl = `${CONFIG.API_BASE}/ad/get?uid=${uid}&site=${CONFIG.SITE_ID}&pageCategory=${pageCategory}&adType=${adType}&limit=1`;

        fetch(apiUrl)
            .then(response => response.json())
            .then(data => {
                if (data.success && data.data && data.data.length > 0) {
                    renderAd(data.data[0], container, pageCategory, uid);
                    if (callback) callback(data.data[0]);
                } else {
                    container.innerHTML = '<div style="text-align:center;padding:20px;color:#999;">暂无广告</div>';
                }
            })
            .catch(error => {
                console.error('【广告平台】加载失败:', error);
                container.innerHTML = '<div style="text-align:center;padding:20px;color:#f44336;">加载失败</div>';
            });
    };

    // ========== 渲染广告 ==========
    function renderAd(ad, container, pageCategory, uid) {
        // 立即上报展示
        const impressionUrl = `${CONFIG.API_BASE}/ad/impression?adId=${ad.adId}&uid=${uid}&site=${CONFIG.SITE_ID}&pageCategory=${pageCategory}`;
        fetch(impressionUrl);

        let html = '';
        const clickUrl = `${CONFIG.API_BASE}/ad/click?adId=${ad.adId}&uid=${uid}&site=${CONFIG.SITE_ID}&target=${encodeURIComponent(ad.targetUrl)}`;

        switch (ad.type) {
            case 'image':
                html = `<a href="${clickUrl}" target="_blank" style="display:block;">
                            <img src="${ad.content}" alt="${ad.title}" style="width:100%;border:none;">
                        </a>`;
                break;
            case 'text':
                html = `<div style="background:#f5f5f5;padding:10px;border-radius:4px;border:1px solid #ddd;">
                            <a href="${clickUrl}" target="_blank" style="text-decoration:none;color:#333;font-weight:bold;">
                                ${ad.title}
                            </a>
                        </div>`;
                break;
            case 'video':
                html = `<video controls style="width:100%;">
                            <source src="${ad.content}" type="video/mp4">
                            您的浏览器不支持视频播放
                        </video>`;
                break;
        }

        container.innerHTML = html;
    }

    // ========== 行为上报 ==========
    window.reportUserBehavior = function(category, action, duration) {
        const uid = getUid();
        const data = {
            uid: uid,
            site: CONFIG.SITE_ID,
            category: category,
            action: action,
            duration: duration || 0
        };

        fetch(`${CONFIG.API_BASE}/user/behavior`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        }).catch(error => console.error('【广告平台】行为上报失败:', error));
    };

    // ========== 自动初始化 ==========
    document.addEventListener('DOMContentLoaded', function() {
        // 延迟附加UID到链接
        setTimeout(appendUidToLinks, 500);

        // 自动上报页面访问
        const pageCategory = window.PAGE_CATEGORY;
        if (pageCategory) {
            setTimeout(() => {
                reportUserBehavior(pageCategory, 'view', 3);
            }, 3000);
        }
    });

})(window, document);