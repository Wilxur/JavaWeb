/**
 * 广告平台跨站追踪与渲染SDK v2.0
 * 文件名：sdk.js（保持原名，不要改）
 * 功能: UID管理、广告渲染、行为采集（保留原分类映射）
 */

(function(window, document) {
    'use strict';

    // ===== 配置（保留原有分类映射）=====
    const CONFIG = {
        API_HOST: 'http://10.100.164.17:8080/ad-platform',  // 各网站需修改为自己的地址
        SITE_ID: 'video',  // 网站标识: video/shopping/news（各网站必须修改！）
        COOKIE_NAME: 'ad_platform_uid',
        COOKIE_DAYS: 365,
        // 分类映射（保留原有映射表）
        CATEGORY_MAP: {
            // ========== 新闻站（唐杰）映射 ==========
            '科技': 'electronics',
            '财经': 'home',
            '体育': 'sports',
            '娱乐': 'beauty',
            '社会': 'home',
            '教育': 'electronics',

            // ========== 购物站（魏瑄）、视频站（王瑞涵）映射 ==========
            '电子产品': 'electronics',
            '服装鞋帽': 'clothing',
            '食品饮料': 'food',
            '美妆护肤': 'beauty',
            '家居用品': 'home',
            '运动户外': 'sports'
        }
    };

    // ==================== UID管理 ====================
    function generateUID() {
        return 'uid-' + Date.now() + '-' + Math.random().toString(36).substr(2, 9);
    }

    function setCookie(name, value, days) {
        const expires = new Date();
        expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
        document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`;
    }

    function getCookie(name) {
        const nameEQ = name + "=";
        const ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i].trim();
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
    function getMappedCategory() {
        const meta = document.querySelector('meta[name="page-category"]');
        if (meta) return meta.getAttribute('content') || 'electronics';

        // 使用配置的映射表
        const rawCategory = document.title || window.location.pathname;
        for (const [key, value] of Object.entries(CONFIG.CATEGORY_MAP)) {
            if (rawCategory.includes(key)) return value;
        }
        return 'electronics'; // 默认
    }

    // ==================== 日志上报 ====================
    function trackImpression(adId, category) {
        const uid = getUserId();
        if (!uid) return;

        const img = new Image();
        img.src = `${CONFIG.API_HOST}/api/track/impression?` +
            `uid=${uid}&adId=${adId}&site=${CONFIG.SITE_ID}&category=${category}`;
        img.style.display = 'none';
        document.body.appendChild(img);
        setTimeout(() => img.remove(), 1000);
    }

    function trackClick(adId, redirectUrl, category) {
        const uid = getUserId();
        if (!uid) return;

        fetch(`${CONFIG.API_HOST}/api/track/click?` +
            `uid=${uid}&adId=${adId}&site=${CONFIG.SITE_ID}&category=${category}` +
            `&redirect=${encodeURIComponent(redirectUrl || '')}`,
            { credentials: 'include' }
        );
    }

    // ==================== 核心渲染API ====================
    const AdPlatformSDK = {
        /**
         * 一键渲染广告到容器（自动上报 & 绑定点击）
         * @param {string} containerId - 容器DOM的ID
         * @param {Object} ad - 广告对象（必须有 id, adType, content, category）
         * @param {Object} options - 可选配置（如自定义样式）
         * @returns {HTMLElement|null} 渲染后的广告元素
         */
        renderAd: function(containerId, ad, options) {
            const container = document.getElementById(containerId);
            if (!container || !ad || !ad.id) {
                console.error('❌ 容器或广告对象无效');
                return null;
            }

            container.innerHTML = ''; // 清空容器

            let adElement = null;
            const category = ad.category || getMappedCategory();

            // 根据类型渲染
            switch (ad.adType) {
                case 'video':
                    adElement = this._createVideoElement(ad, category);
                    break;
                case 'image':
                    adElement = this._createImageElement(ad, category);
                    break;
                case 'text':
                    adElement = this._createTextElement(ad, category);
                    break;
                default:
                    console.error('❌ 不支持的 adType:', ad.adType);
                    return null;
            }

            container.appendChild(adElement);
            return adElement;
        },

        // 创建视频元素（返回元素，播放逻辑由调用方控制）
        _createVideoElement: function(ad, category) {
            const video = document.createElement('video');
            video.src = ad.content;
            video.controls = true;
            video.autoplay = true;
            video.muted = true;
            video.playsInline = true;
            video.style.width = '100%';
            video.style.borderRadius = '8px';
            video.dataset.adId = ad.id;
            video.dataset.category = category;
            return video;
        },

        // 创建图片元素（自动上报 & 绑定点击）
        _createImageElement: function(ad, category) {
            const img = document.createElement('img');
            img.src = ad.content;
            img.alt = ad.title || '广告图片';
            img.style.maxWidth = '100%';
            img.style.cursor = 'pointer';
            img.style.borderRadius = '8px';

            // 自动上报展示
            trackImpression(ad.id, category);

            // 绑定点击（上报+跳转）
            img.onclick = () => {
                trackClick(ad.id, ad.content, category);
                window.open(ad.content, '_blank');
            };

            return img;
        },

        // 创建文字元素（自动上报 & 绑定点击）
        _createTextElement: function(ad, category) {
            const div = document.createElement('div');
            div.style.padding = '12px';
            div.style.background = '#f5f5f5';
            div.style.borderRadius = '8px';
            div.style.cursor = 'pointer';

            const link = document.createElement('a');
            link.href = ad.content;
            link.innerText = ad.title || ad.content;
            link.style.textDecoration = 'none';
            link.style.color = '#333';
            link.style.fontWeight = '500';

            // 自动上报展示
            trackImpression(ad.id, category);

            // 绑定点击
            link.onclick = (e) => {
                e.preventDefault();
                trackClick(ad.id, ad.content, category);
                window.open(ad.content, '_blank');
            };

            div.appendChild(link);
            return div;
        },

        /**
         * 设置站点ID（必须调用）
         * @param {string} siteId - video/shopping/news
         */
        setSite: function(siteId) {
            CONFIG.SITE_ID = siteId;
        },

        /**
         * 设置自定义分类映射（可选）
         * @param {Object} map - 映射表
         */
        setCategoryMap: function(map) {
            Object.assign(CONFIG.CATEGORY_MAP, map);
        },

        /**
         * 获取用户ID（手动上报时使用）
         * @returns {string} UID
         */
        getUserId: function() {
            return getUserId();
        }
    };

    // ==================== 暴露到全局 ====================
    window.AdPlatformSDK = AdPlatformSDK;

})(window, document);