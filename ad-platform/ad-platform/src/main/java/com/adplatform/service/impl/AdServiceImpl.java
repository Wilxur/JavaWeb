package com.adplatform.service.impl;

import com.adplatform.dao.AdDao;
import com.adplatform.dao.impl.AdDaoImpl;
import com.adplatform.model.Ad;
import com.adplatform.model.User;
import com.adplatform.service.AdService;
import java.util.List;

public class AdServiceImpl implements AdService {
    private AdDao adDao = new AdDaoImpl();

    @Override
    public List<Ad> findMyAds(int advertiserId) {
        return adDao.findByAdvertiserId(advertiserId);
    }

    @Override
    public Ad findById(int id, int advertiserId) {
        Ad ad = adDao.findById(id);
        // 权限验证：只能看自己创建的广告
        if (ad != null && ad.getAdvertiserId() == advertiserId) {
            return ad;
        }
        return null;
    }

    @Override
    public boolean save(Ad ad, int advertiserId) {
        // 设置广告主ID
        ad.setAdvertiserId(advertiserId);

        // 默认状态为上线(1)
        if (ad.getStatus() == null) {
            ad.setStatus(1);
        }

        int result;
        if (ad.getId() == null) {
            // 新增
            result = adDao.insert(ad);
        } else {
            // 编辑：先验证权限
            Ad existing = adDao.findById(ad.getId());
            if (existing == null || existing.getAdvertiserId() != advertiserId) {
                return false; // 无权编辑
            }
            result = adDao.update(ad);
        }
        return result > 0;
    }

    @Override
    public boolean delete(int adId, int advertiserId) {
        // 验证权限：只能删除自己的广告
        Ad ad = adDao.findById(adId);
        if (ad == null || ad.getAdvertiserId() != advertiserId) {
            return false;
        }
        return adDao.delete(adId) > 0;
    }

    @Override
    public boolean toggleStatus(int adId, int status) {
        // 切换状态（上下线）
        return adDao.updateStatus(adId, status) > 0;
    }
}