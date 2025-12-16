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

    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 成功返回true，失败返回false（旧密码错误）
     */
    boolean changePassword(int userId, String oldPassword, String newPassword);
}