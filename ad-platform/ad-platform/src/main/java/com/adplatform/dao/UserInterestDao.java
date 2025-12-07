package com.adplatform.dao;

import com.adplatform.model.UserInterest;
import com.adplatform.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserInterestDao {

    public List<UserInterest> getTopInterests(String uid, int limit) {
        List<UserInterest> interests = new ArrayList<>();
        String sql = "SELECT * FROM user_interests WHERE uid=? ORDER BY score DESC LIMIT ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uid);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserInterest interest = new UserInterest();
                interest.setUid(rs.getString("uid"));
                interest.setCategory(rs.getString("category"));
                interest.setScore(rs.getInt("score"));
                interest.setLastUpdated(rs.getString("last_updated"));
                interests.add(interest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return interests;
    }

    public void updateInterest(String uid, String category, int increment) {
        String sql = "INSERT INTO user_interests (uid, category, score) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE score = score + ?, last_updated = NOW()";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uid);
            pstmt.setString(2, category);
            pstmt.setInt(3, increment);
            pstmt.setInt(4, increment);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ensureUserExists(String uid) {
        String sql = "INSERT IGNORE INTO anonymous_users (uid) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}