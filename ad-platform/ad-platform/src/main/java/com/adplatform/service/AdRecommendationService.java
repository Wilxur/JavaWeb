package com.adplatform.service;

import com.adplatform.model.Ad;

public interface AdRecommendationService {
    /**
     * 为匿名用户推荐广告（支持站点类型过滤）
     * @param uid 用户唯一标识
     * @param pageCategory 当前页面分类
     * @param site 站点类型：video/shopping/news
     * @return 推荐广告（null表示无可用广告）
     */
    Ad recommend(String uid, String pageCategory, String site);
}