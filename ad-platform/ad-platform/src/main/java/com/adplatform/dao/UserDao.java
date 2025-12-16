package com.adplatform.dao;

import com.adplatform.model.User;

public interface UserDao {
    int insert(User user);
    User findByUsername(String username);
    User findById(int id);
    int update(User user);
}