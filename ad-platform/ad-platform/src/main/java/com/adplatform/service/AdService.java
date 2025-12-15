package com.adplatform.service;

import com.adplatform.model.Ad;
import java.util.List;

public interface AdService {
    // 查询当前用户的所有广告
    List<Ad> findMyAds(int advertiserId);

    // 根据ID查询广告（带权限验证）
    Ad findById(int id, int advertiserId);

    // 保存广告（新增或编辑）
    boolean save(Ad ad, int advertiserId);

    // 删除广告（带权限验证）
    boolean delete(int adId, int advertiserId);

    // 切换广告状态（上下线）
    boolean toggleStatus(int adId, int status);
}