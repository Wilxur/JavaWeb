package com.adplatform.service;

import com.adplatform.dao.AdDao;
import com.adplatform.dao.UserInterestDao;
import com.adplatform.model.Ad;
import com.adplatform.model.UserInterest;

import java.util.ArrayList;
import java.util.List;

public class AdService {
    private AdDao adDao = new AdDao();
    private UserInterestDao userDao = new UserInterestDao();

    public List<Ad> getPersonalizedAds(String uid, String site, String pageCategory, String adType, int limit) {
        userDao.ensureUserExists(uid);

        List<String> categories = new ArrayList<>();
        categories.add(pageCategory);

        // 获取用户兴趣Top3
        List<UserInterest> interests = userDao.getTopInterests(uid, 3);
        for (UserInterest interest : interests) {
            if (!categories.contains(interest.getCategory())) {
                categories.add(interest.getCategory());
            }
        }

        // 查询广告
        List<Ad> ads = adDao.findAds(adType, categories, limit);

        // 补足热门广告
        if (ads.size() < limit) {
            List<Ad> hotAds = adDao.getHotAds(adType, limit - ads.size());
            for (Ad ad : hotAds) {
                if (!ads.contains(ad)) {
                    ads.add(ad);
                }
            }
        }

        return ads.subList(0, Math.min(limit, ads.size()));
    }

    public void logImpression(int adId, String uid, String site, String pageCategory) {
        adDao.logImpression(adId, uid, site, pageCategory);
    }

    public void logClick(int adId, String uid, String site) {
        adDao.logClick(adId, uid, site);
    }

    public int addAd(Ad ad) {
        return adDao.addAd(ad);
    }

    public List<Ad> getAllAds() {
        return adDao.getAllAds();
    }
}