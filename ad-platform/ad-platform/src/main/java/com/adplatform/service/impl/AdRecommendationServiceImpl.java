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

public class AdRecommendationServiceImpl implements AdRecommendationService {
    private UserInterestDao userInterestDao = new UserInterestDaoImpl();
    private AdDao adDao = new AdDaoImpl();

    private static final int TOP_INTERESTS_LIMIT = 5; // 取TOP5兴趣
    private static final double CURRENT_CATEGORY_BOOST = 1.5; // 当前分类权重提升

    @Override
    public Ad recommend(String uid, String pageCategory) {
        // 1. 获取用户TOP5兴趣
        List<UserInterest> userInterests = userInterestDao.findTopByUid(uid, TOP_INTERESTS_LIMIT);

        // 2. 构建权重Map: category -> weight
        Map<String, Double> categoryWeights = new HashMap<>();
        int totalScore = userInterests.stream().mapToInt(UserInterest::getScore).sum();

        for (UserInterest interest : userInterests) {
            double weight = totalScore > 0 ? (double) interest.getScore() / totalScore : 0.1;
            categoryWeights.put(interest.getCategory(), weight);
        }

        // 3. 保底：当前页面分类至少0.1权重，如果在TOP5中则提升
        categoryWeights.putIfAbsent(pageCategory, 0.1);
        if (categoryWeights.containsKey(pageCategory)) {
            categoryWeights.put(pageCategory, categoryWeights.get(pageCategory) * CURRENT_CATEGORY_BOOST);
        }

        // 4. 查询候选广告（按权重高的分类优先）
        List<String> candidateCategories = categoryWeights.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Ad> candidateAds = adDao.findByCategories(candidateCategories);

        if (candidateAds.isEmpty()) {
            // 冷启动：返回最新广告
            return getFallbackAd(pageCategory);
        }

        // 5. 按权重排序并返回最优
        return candidateAds.stream()
                .max(Comparator.comparingDouble(ad ->
                        categoryWeights.getOrDefault(ad.getCategory(), 0.0)))
                .orElse(candidateAds.get(0));
    }

    /**
     * 冷启动兜底：返回当前分类的最新广告
     */
    private Ad getFallbackAd(String pageCategory) {
        List<String> categories = Collections.singletonList(pageCategory);
        List<Ad> ads = adDao.findByCategories(categories);
        return ads.isEmpty() ? null : ads.get(0);
    }
}