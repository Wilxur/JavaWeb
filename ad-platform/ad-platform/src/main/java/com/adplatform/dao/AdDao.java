package com.adplatform.dao;

import com.adplatform.model.Ad;
import com.adplatform.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdDao {

    public List<Ad> findAds(String adType, List<String> categories, int limit) {
        List<Ad> ads = new ArrayList<>();
        if (categories == null || categories.isEmpty()) {
            return ads;
        }

        String placeholders = String.join(",", categories.stream().map(c -> "?").toArray(String[]::new));
        String sql = "SELECT * FROM ads WHERE status=1 AND ad_type=? AND category IN (" + placeholders + ") ORDER BY RAND() LIMIT ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adType);
            for (int i = 0; i < categories.size(); i++) {
                pstmt.setString(i + 2, categories.get(i));
            }
            pstmt.setInt(categories.size() + 2, limit);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ads.add(mapToAd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ads;
    }

    public List<Ad> getHotAds(String adType, int limit) {
        List<Ad> ads = new ArrayList<>();
        String sql = "SELECT * FROM ads WHERE status=1 AND ad_type=? ORDER BY RAND() LIMIT ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adType);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ads.add(mapToAd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ads;
    }

    public void logImpression(int adId, String uid, String site, String pageCategory) {
        String sql = "INSERT INTO ad_impressions (ad_id, uid, site, page_category) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adId);
            pstmt.setString(2, uid);
            pstmt.setString(3, site);
            pstmt.setString(4, pageCategory);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logClick(int adId, String uid, String site) {
        String sql = "INSERT INTO ad_clicks (ad_id, uid, site) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adId);
            pstmt.setString(2, uid);
            pstmt.setString(3, site);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addAd(Ad ad) {
        String sql = "INSERT INTO ads (advertiser_id, title, ad_type, content, category, target_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, ad.getAdvertiserId());
            pstmt.setString(2, ad.getTitle());
            pstmt.setString(3, ad.getAdType());
            pstmt.setString(4, ad.getContent());
            pstmt.setString(5, ad.getCategory());
            pstmt.setString(6, ad.getTargetUrl());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Ad> getAllAds() {
        List<Ad> ads = new ArrayList<>();
        String sql = "SELECT * FROM ads ORDER BY created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ads.add(mapToAd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ads;
    }

    private Ad mapToAd(ResultSet rs) throws SQLException {
        Ad ad = new Ad();
        ad.setId(rs.getInt("id"));
        ad.setAdvertiserId(rs.getInt("advertiser_id"));
        ad.setTitle(rs.getString("title"));
        ad.setAdType(rs.getString("ad_type"));
        ad.setContent(rs.getString("content"));
        ad.setCategory(rs.getString("category"));

        String tagsJson = rs.getString("tags");
        if (tagsJson != null && !tagsJson.isEmpty()) {
            ad.setTags(Arrays.asList(tagsJson.replace("[", "").replace("]", "").replace("\"", "").split(",")));
        }

        ad.setStatus(rs.getInt("status"));
        ad.setTargetUrl(rs.getString("target_url"));
        ad.setCreatedAt(rs.getString("created_at"));

        // 生成上报URL
        ad.setImpressionUrl("/api/ad/impression?adId=" + ad.getId());
        ad.setClickUrl("/api/ad/click?adId=" + ad.getId());

        return ad;
    }
}