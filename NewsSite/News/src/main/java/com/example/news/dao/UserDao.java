package com.example.news.dao;

import com.example.news.model.User;

public interface UserDao {

    /**
     * 根据用户名查询用户（用于注册校验 / 登录）
     */
    User findByUsername(String username);

    /**
     * 保存新用户（注册）
     */
    int save(User user);

}