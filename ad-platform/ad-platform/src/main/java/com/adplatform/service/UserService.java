package com.adplatform.service;

import com.adplatform.model.User;

public interface UserService {
    /**
     * 用户注册
     * @param username 用户名
     * @param password 明文密码
     * @param email 邮箱
     * @return 注册成功返回User对象，失败返回null
     */
    User register(String username, String password, String email);

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 明文密码
     * @return 验证成功返回User对象，失败返回null
     */
    User login(String username, String password);

    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);
}