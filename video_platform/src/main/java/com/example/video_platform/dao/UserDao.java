package com.example.video_platform.dao;

import com.example.video_platform.model.User;

public interface UserDao {
    int insert(String username, String passwordHash);
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String passwordHash);
}
