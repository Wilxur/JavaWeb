package com.example.video_platform.service;

import com.example.video_platform.model.User;

public interface UserService {
    void register(String username, String password, String captchaInput, String captchaSession);
    User login(String username, String password, String captchaInput, String captchaSession);
}
