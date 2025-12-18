package com.adplatform.dao.impl;

import com.adplatform.dao.UserInterestDao;
import com.adplatform.model.UserInterest;
import com.adplatform.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserInterestDaoImpl implements UserInterestDao {

    private static final DBUtil.RowMapper<UserInterest> MAPPER = rs -> {
        UserInterest interest = new UserInterest();
        interest.setUid(rs.getString("uid"));
        interest.setCategory(rs.getString("category"));
        interest.setScore(rs.getInt("score"));
        interest.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        return interest;
    };

    @Override
    public List<UserInterest> findTopByUid(String uid, int limit) {
        String sql = "SELECT * FROM user_interests WHERE uid = ? ORDER BY score DESC LIMIT ?";
        return DBUtil.executeQuery(sql, MAPPER, uid, limit);
    }

    @Override
    public UserInterest findByUidAndCategory(String uid, String category) {
        String sql = "SELECT * FROM user_interests WHERE uid = ? AND category = ? LIMIT 1";
        return DBUtil.executeQuery(sql, MAPPER, uid, category)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public int upsert(String uid, String category, int increment) {
        String sql = "INSERT INTO user_interests(uid, category, score, last_updated) " +
                "VALUES(?, ?, ?, NOW()) " +
                "ON DUPLICATE KEY UPDATE " +
                "score = score + ?, last_updated = NOW()";
        return DBUtil.executeUpdate(sql, uid, category, increment, increment);
    }

    @Override
    public int decayOldInterests(String uid, String excludeCategory, int decayValue) {
        String sql = "UPDATE user_interests SET " +
                "score = GREATEST(0, score - ?), " + // 保证不<0
                "last_updated = NOW() " +
                "WHERE uid = ? AND category != ? AND score > 0";
        return DBUtil.executeUpdate(sql, decayValue, uid, excludeCategory);
    }
}