package com.adplatform.dao;

import com.adplatform.model.Ad;
import java.util.List;

public interface AdDao {
    // 查询某用户的所有广告
    List<Ad> findByAdvertiserId(int advertiserId);

    // 根据ID查询单条广告
    Ad findById(int id);

    // 插入新广告
    int insert(Ad ad);

    // 更新广告
    int update(Ad ad);

    // 删除广告
    int delete(int id);

    // 切换广告状态（上下线）
    int updateStatus(int id, int status);
}