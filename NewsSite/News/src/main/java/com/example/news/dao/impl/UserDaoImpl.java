package com.example.news.dao.impl;

import com.example.news.dao.UserDao;
import com.example.news.model.User;
import com.example.news.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String SQL_FIND_BY_USERNAME =
            "SELECT id, username, password, created_at FROM user WHERE username = ?";

    private static final String SQL_INSERT =
            "INSERT INTO user (username, password) VALUES (?, ?)";

    @Override
    public User findByUsername(String username) {
        List<User> list = DBUtil.executeQuery(
                SQL_FIND_BY_USERNAME,
                rs -> mapRow(rs),
                username
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int save(User user) {
        return DBUtil.executeUpdate(
                SQL_INSERT,
                user.getUsername(),
                user.getPassword()
        );
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}