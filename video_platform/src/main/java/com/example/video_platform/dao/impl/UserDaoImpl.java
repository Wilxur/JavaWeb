package com.example.video_platform.dao.impl;

import com.example.video_platform.dao.UserDao;
import com.example.video_platform.model.User;
import com.example.video_platform.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    @Override
    public int insert(String username, String passwordHash) {
        String sql = "INSERT INTO user(username, password) VALUES (?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, create_time FROM user WHERE username = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setCreateTime(String.valueOf(rs.getTimestamp("create_time")));
                return u;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String passwordHash) {
        String sql = "SELECT id, username, password, create_time FROM user WHERE username = ? AND password = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setCreateTime(String.valueOf(rs.getTimestamp("create_time")));
                return u;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
