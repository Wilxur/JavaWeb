package com.adplatform.service.impl;

import com.adplatform.dao.UserDao;
import com.adplatform.dao.impl.UserDaoImpl;
import com.adplatform.model.User;
import com.adplatform.service.UserService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User register(String username, String password, String email) {
        // 1. 检查用户名是否已存在
        if (userDao.findByUsername(username) != null) {
            return null; // 用户名已存在
        }

        // 2. 密码加密（简单MD5实现，生产环境建议用BCrypt）
        String encryptedPassword = encryptPassword(password);

        // 3. 创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPassword);
        user.setEmail(email);
        user.setRole("advertiser"); // 默认角色为广告主

        // 4. 插入数据库
        int result = userDao.insert(user);
        if (result > 0) {
            return userDao.findByUsername(username); // 返回完整对象（含id）
        }
        return null;
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(encryptPassword(password))) {
            return user;
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * MD5密码加密（简单实现）
     * 生产环境建议使用BCryptPasswordEncoder
     */
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }
}