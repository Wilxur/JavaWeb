package com.example.video_platform.service.impl;

import com.example.video_platform.dao.UserDao;
import com.example.video_platform.dao.impl.UserDaoImpl;
import com.example.video_platform.model.User;
import com.example.video_platform.service.UserService;
import com.example.video_platform.util.PasswordUtil;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void register(String username, String password, String captchaInput, String captchaSession) {
        if (captchaSession == null || captchaInput == null || !captchaSession.equalsIgnoreCase(captchaInput)) {
            throw new RuntimeException("验证码错误");
        }
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        if (userDao.findByUsername(username) != null) {
            throw new RuntimeException("用户名已存在");
        }
        userDao.insert(username, PasswordUtil.sha256(password));
    }

    @Override
    public User login(String username, String password, String captchaInput, String captchaSession) {
        if (captchaSession == null || !captchaSession.equalsIgnoreCase(captchaInput)) {
            throw new RuntimeException("验证码错误");
        }
        String hash = PasswordUtil.sha256(password);
        return userDao.findByUsernameAndPassword(username, hash);
    }
}
