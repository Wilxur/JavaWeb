package com.adplatform.dao.impl;

import com.adplatform.dao.AdDao;
import com.adplatform.model.Ad;
import com.adplatform.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdDaoImpl implements AdDao {

    // 结果集映射器
    private static final DBUtil.RowMapper<Ad> AD_MAPPER = rs -> {
        Ad ad = new Ad();
        ad.setId(rs.getInt("id"));
        ad.setAdvertiserId(rs.getInt("advertiser_id"));
        ad.setTitle(rs.getString("title"));
        ad.setAdType(rs.getString("ad_type"));
        ad.setContent(rs.getString("content"));
        ad.setCategory(rs.getString("category"));
        ad.setTags(rs.getString("tags"));
        ad.setStatus(rs.getInt("status"));
        ad.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return ad;
    };

    @Override
    public List<Ad> findByAdvertiserId(int advertiserId) {
        String sql = "SELECT * FROM ads WHERE advertiser_id = ? ORDER BY created_at DESC";
        return DBUtil.executeQuery(sql, AD_MAPPER, advertiserId);
    }

    @Override
    public Ad findById(int id) {
        String sql = "SELECT * FROM ads WHERE id = ?";
        return DBUtil.executeQuery(sql, AD_MAPPER, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public int insert(Ad ad) {
        String sql = "INSERT INTO ads(advertiser_id, title, ad_type, content, category, tags, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
        return DBUtil.executeUpdate(sql,
                ad.getAdvertiserId(),
                ad.getTitle(),
                ad.getAdType(),
                ad.getContent(),
                ad.getCategory(),
                ad.getTags(),
                ad.getStatus()
        );
    }

    @Override
    public int update(Ad ad) {
        String sql = "UPDATE ads SET title = ?, ad_type = ?, content = ?, category = ?, tags = ? WHERE id = ? AND advertiser_id = ?";
        return DBUtil.executeUpdate(sql,
                ad.getTitle(),
                ad.getAdType(),
                ad.getContent(),
                ad.getCategory(),
                ad.getTags(),
                ad.getId(),
                ad.getAdvertiserId()
        );
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM ads WHERE id = ?";
        return DBUtil.executeUpdate(sql, id);
    }

    @Override
    public int updateStatus(int id, int status) {
        String sql = "UPDATE ads SET status = ? WHERE id = ?";
        return DBUtil.executeUpdate(sql, status, id);
    }
}