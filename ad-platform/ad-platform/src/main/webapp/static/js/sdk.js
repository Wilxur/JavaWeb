/**
 * 广告平台跨站追踪SDK v1.0
 * 功能: UID管理、广告渲染、行为采集
 */
(function(window, document) {
    'use strict';

    // 配置（生产环境建议从服务端动态生成）
    const CONFIG = {
        API_HOST: 'http://10.100.164.17:8080/ad-platform',  // 你的服务器IP + 上下文路径
        COOKIE_NAME: 'ad_platform_uid',
        COOKIE_DAYS: 365,
        // 分类映射（各站点需自行配置）
        CATEGORY_MAP: {
            // ========== 新闻站（唐杰）映射 ==========
            '科技': 'electronics',      // 科技新闻 → 电子产品（电脑、手机、数码）
            '财经': 'home',             // 财经新闻 → 家居用品（品质生活、理财工具）
            '体育': 'sports',           // 体育新闻 → 运动户外（运动装备、健身器材）
            '娱乐': 'beauty',           // 娱乐新闻 → 美妆护肤（时尚、潮流）
            '社会': 'home',             // 社会新闻 → 家居用品（民生、日常）
            '教育': 'electronics',      // 教育新闻 → 电子产品（学习设备、在线教育工具）

            // ========== 购物站（魏瑄）、视频站（王瑞涵）映射 ==========
            '电子产品': 'electronics',    // 科技视频 → 电子产品
            '服装鞋帽': 'clothing',       // 时尚视频 → 服装鞋帽
            '食品饮料': 'food',           // 美食视频 → 食品饮料
            '美妆护肤': 'beauty',         // 美妆视频 → 美妆护肤
            '家居用品': 'home',           // 生活视频 → 家居用品
            '运动户外': 'sports'          // 运动视频 → 运动户外
        }
    };

    // ==================== UID管理 ====================
    function generateUID() {
        return 'uid-' + Date.now() + '-' + Math.random().toString(36).substr(2, 9);
    }

    function setCookie(name, value, days) {
        const expires = new Date();
        expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
        document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/;domain=.ad-platform.com`;
    }

    function getCookie(name) {
        const nameEQ = name + "=";
        const ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }

    function getUserId() {
        let uid = getCookie(CONFIG.COOKIE_NAME);
        if (!uid) {
            uid = generateUID();
            setCookie(CONFIG.COOKIE_NAME, uid, CONFIG.COOKIE_DAYS);
        }
        return uid;
    }

    // ==================== 分类识别 ====================
    function getCategory() {
        // 方法1: 从页面meta标签读取
        const metaCategory = document.querySelector('meta[name="ad-category"]');
        if (metaCategory) {
            return metaCategory.getAttribute('content');
        }

        // 方法2: 从URL路径识别
        const path = window.location.pathname;
        if (path.includes('/tech/') || path.includes('/electronics/')) return 'electronics';
        if (path.includes('/food/')) return 'food';
        if (path.includes('/beauty/')) return 'beauty';
        if (path.includes('/sports/')) return 'sports';
        if (path.includes('/home/')) return 'home';
        if (path.includes('/clothing/')) return 'clothing';

        // 方法3: 从页面标题/内容智能识别（简单版）
        const title = document.title.toLowerCase();
        if (title.includes('科技') || title.includes('数码')) return 'electronics';
        if (title.includes('美食') || title.includes('食品')) return 'food';

        // 默认值
        return 'electronics';
    }

    function getMappedCategory() {
        const rawCategory = getCategory();
        return CONFIG.CATEGORY_MAP[rawCategory] || rawCategory;
    }

    // ==================== 广告渲染 ====================
    function createAdElement(ad) {
        if (!ad) return null;

        const container = document.getElementById('ad-container') || document.body;
        const adElement = document.createElement('div');
        adElement.className = 'ad-platform-ad';
        adElement.style.cssText = 'border: 1px solid #eee; padding: 10px; margin: 10px 0;';

        // 根据广告类型渲染
        if (ad.adType === 'text') {
            adElement.innerHTML = `
                <h5>${ad.title}</h5>
                <p>${ad.content}</p>
                <small style="color:#999">广告</small>
            `;
        } else if (ad.adType === 'image') {
            adElement.innerHTML = `
                <img src="${ad.content}" style="max-width:100%;" alt="${ad.title}">
                <p style="margin-top:5px;">${ad.title}</p>
            `;
        } else if (ad.adType === 'video') {
            adElement.innerHTML = `
                <video controls style="max-width:100%;">
                    <source src="${ad.content}" type="video/mp4">
                </video>
                <p>${ad.title}</p>
            `;
        }

        // 绑定点击事件
        adElement.addEventListener('click', function() {
            trackClick(ad.id, ad.content);
        });

        container.appendChild(adElement);
        return adElement;
    }

    // ==================== 行为追踪 ====================
    function trackImpression(adId, category) {
        const uid = getUserId();
        const img = new Image();
        img.src = `${CONFIG.API_HOST}/api/track/impression?uid=${uid}&adId=${adId}&site=${window.location.host}&category=${category}`;
        img.style.display = 'none';
        document.body.appendChild(img);
    }

    function trackClick(adId, redirectUrl) {
        const uid = getUserId();
        const category = getMappedCategory();

        // 异步上报点击
        fetch(`${CONFIG.API_HOST}/api/track/click?uid=${uid}&adId=${adId}&site=${window.location.host}&category=${category}&redirect=${encodeURIComponent(redirectUrl)}`, {
            method: 'GET',
            credentials: 'include'
        });
    }

    // ==================== 主函数 ====================
    function initAd() {
        const uid = getUserId();
        const category = getMappedCategory();

        // 获取广告
        fetch(`${CONFIG.API_HOST}/api/ad/get?uid=${uid}&category=${category}`, {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(data => {
                if (data.success && data.ad) {
                    const ad = data.ad;
                    createAdElement(ad);
                    // 上报展示
                    trackImpression(ad.id, category);
                } else {
                    console.warn('广告获取失败:', data.message);
                }
            })
            .catch(err => {
                console.error('广告加载错误:', err);
            });
    }

    // ==================== 对外API ====================
    const AdPlatformSDK = {
        // 获取当前用户ID
        getUserId: getUserId,

        // 手动更新兴趣（用于页面无广告时）
        updateInterest: function(category, increment) {
            const uid = getUserId();
            fetch(`${CONFIG.API_HOST}/api/user/interest`, {
                method: 'POST',
                credentials: 'include',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ uid: uid, category: category })
            });
        },

        // 手动渲染广告（用于动态加载）
        renderAd: function(containerId) {
            initAd();
        },

        // 设置分类映射（各站点可自定义）
        setCategoryMap: function(map) {
            Object.assign(CONFIG.CATEGORY_MAP, map);
        }
    };

    // ==================== 自动初始化 ====================
    // 页面加载完成后自动执行
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initAd);
    } else {
        initAd();
    }

    // 暴露到全局
    window.AdPlatformSDK = AdPlatformSDK;

})(window, document);