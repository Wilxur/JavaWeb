package com.adplatform.service.impl;

import com.adplatform.dao.UserInterestDao;
import com.adplatform.dao.impl.UserInterestDaoImpl;
import com.adplatform.service.UserInterestService;

public class UserInterestServiceImpl implements UserInterestService {
    private UserInterestDao userInterestDao = new UserInterestDaoImpl();

    @Override
    public void updateInterest(String uid, String category, int increment) {
        if (uid == null || category == null) return;
        userInterestDao.upsert(uid, category, increment);
    }

    @Override
    public void decayOldInterests(String uid, String excludeCategory) {
        if (uid == null) return;
        // 衰减值1，可配置
        userInterestDao.decayOldInterests(uid, excludeCategory, 1);
    }
}