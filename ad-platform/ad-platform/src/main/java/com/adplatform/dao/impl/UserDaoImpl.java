package com.adplatform.dao.impl;

import com.adplatform.dao.UserDao;
import com.adplatform.model.User;
import com.adplatform.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    // 结果集映射器
    private static final DBUtil.RowMapper<User> USER_MAPPER = rs -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return user;
    };

    @Override
    public int insert(User user) {
        String sql = "INSERT INTO users(username, password, email, role) VALUES(?, ?, ?, ?)";
        return DBUtil.executeUpdate(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? LIMIT 1";
        return DBUtil.executeQuery(sql, USER_MAPPER, username)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
        return DBUtil.executeQuery(sql, USER_MAPPER, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public int update(User user) {
        String sql = "UPDATE users SET password = ?, email = ?, role = ? WHERE id = ?";
        return DBUtil.executeUpdate(sql,
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getId()
        );
    }
}