package com.adplatform.service.impl;

import com.adplatform.dao.AdDao;
import com.adplatform.dao.UserInterestDao;
import com.adplatform.dao.impl.AdDaoImpl;
import com.adplatform.dao.impl.UserInterestDaoImpl;
import com.adplatform.model.Ad;
import com.adplatform.model.UserInterest;
import com.adplatform.service.AdRecommendationService;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Collections;

public class AdRecommendationServiceImpl implements AdRecommendationService {
    private UserInterestDao userInterestDao = new UserInterestDaoImpl();
    private AdDao adDao = new AdDaoImpl();

    private static final int TOP_INTERESTS_LIMIT = 5;
    private static final double CURRENT_CATEGORY_BOOST = 1.5;

    @Override
    public Ad recommend(String uid, String pageCategory, String site) {
        // 1. 获取用户TOP5兴趣
        List<UserInterest> userInterests = userInterestDao.findTopByUid(uid, TOP_INTERESTS_LIMIT);

        // 2. 构建权重Map
        Map<String, Double> categoryWeights = new HashMap<>();
        int totalScore = userInterests.stream().mapToInt(UserInterest::getScore).sum();

        for (UserInterest interest : userInterests) {
            double weight = totalScore > 0 ? (double) interest.getScore() / totalScore : 0.1;
            categoryWeights.put(interest.getCategory(), weight);
        }

        // 3. 添加当前页面分类权重
        categoryWeights.putIfAbsent(pageCategory, 0.1);
        if (categoryWeights.containsKey(pageCategory)) {
            categoryWeights.put(pageCategory, categoryWeights.get(pageCategory) * CURRENT_CATEGORY_BOOST);
        }

        // 4. ★★★ 根据站点类型确定广告类型 ★★★
        List<String> allowedAdTypes = getAllowedAdTypes(site); // ["video"] 或 ["image","text"]

        // 5. 查询候选广告（按权重高的分类优先）
        List<String> candidateCategories = categoryWeights.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Ad> candidateAds = adDao.findByCategoriesAndTypes(candidateCategories, allowedAdTypes);

        if (candidateAds.isEmpty()) {
            // 冷启动：返回最新广告（但仍要过滤类型）
            return getFallbackAd(pageCategory, allowedAdTypes);
        }

        // 6. ★★★ 随机选择，避免总是同一个广告 ★★★
        Collections.shuffle(candidateAds); // 打乱顺序
        return candidateAds.get(0); // 随机取第一个
    }

    /**
     * ★★★ 根据站点类型返回允许的广告类型 ★★★
     */
    private List<String> getAllowedAdTypes(String site) {
        if (site == null) return Arrays.asList("image", "text"); // 默认

        switch (site.toLowerCase()) {
            case "video":
                return Collections.singletonList("video");
            case "shopping":
                return Arrays.asList("image"); // 购物站可以图片+文字
            case "news":
                return Collections.singletonList("text"); // 新闻站只显示文字（加载快）
            default:
                return Arrays.asList("image", "text");
        }
    }

    /**
     * 冷启动兜底：返回指定类型的新广告
     */
    private Ad getFallbackAd(String pageCategory,List<String> allowedTypes) {
        List<String> categories = Collections.singletonList(pageCategory);
        List<Ad> ads = adDao.findByCategoriesAndTypes(categories, allowedTypes);
        return ads.isEmpty() ? null : ads.get(0);
    }
}