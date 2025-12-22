package com.adplatform.dao;

import com.adplatform.model.UserInterest;
import java.util.List;

public interface UserInterestDao {
    // 查询用户TOP N兴趣（按score倒序）
    List<UserInterest> findTopByUid(String uid, int limit);

    // 按UID和分类精确查询
    UserInterest findByUidAndCategory(String uid, String category);

    // 更新或插入兴趣（存在则+increment，不存在则新建）
    int upsert(String uid, String category, int increment);

    // 衰减旧兴趣（除指定分类外，所有score>0的减decayValue）
    int decayOldInterests(String uid, String excludeCategory, int decayValue);
}