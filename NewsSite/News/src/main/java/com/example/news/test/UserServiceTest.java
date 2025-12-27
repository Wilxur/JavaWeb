package com.example.news.test;

import com.example.news.model.User;
import com.example.news.service.UserService;

public class UserServiceTest {

    public static void main(String[] args) {

        UserService userService = new UserService();

        // 1) 注册：第一次应成功（true）
        boolean ok1 = userService.register("alice", "123456");
        System.out.println("register alice -> " + ok1);

        // 2) 注册：重复用户名应失败（false）
        boolean ok2 = userService.register("alice", "999999");
        System.out.println("register alice again -> " + ok2);

        // 3) 登录：正确密码应返回 User（非 null）
        User u1 = userService.login("alice", "123456");
        System.out.println("login alice correct -> " + u1);

        // 4) 登录：错误密码应返回 null
        User u2 = userService.login("alice", "wrong");
        System.out.println("login alice wrong -> " + u2);
    }
}