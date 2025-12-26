package com.example.news.service;

import com.example.news.dao.UserDao;
import com.example.news.dao.impl.UserDaoImpl;
import com.example.news.model.User;

public class UserService {

    private final UserDao userDao = new UserDaoImpl();

    /**
     * 注册：成功返回 true；用户名已存在返回 false
     */
    public boolean register(String username, String password) {
        // 1) 参数基本校验（最小必要）
        if (isBlank(username) || isBlank(password)) {
            return false;
        }

        // 2) 用户名是否已存在
        User existing = userDao.findByUsername(username);
        if (existing != null) {
            return false;
        }

        // 3) 保存
        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password); // 课程设计阶段：先明文；后续可替换为哈希
        return userDao.save(user) == 1;
    }

    /**
     * 登录：成功返回 User；失败返回 null
     */
    public User login(String username, String password) {
        if (isBlank(username) || isBlank(password)) {
            return null;
        }

        User user = userDao.findByUsername(username.trim());
        if (user == null) {
            return null;
        }

        // 明文比对（课程设计阶段先这样做；后续可替换为 hash 验证）
        return password.equals(user.getPassword()) ? user : null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}