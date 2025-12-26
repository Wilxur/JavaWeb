package com.example.news.test;

import com.example.news.dao.UserDao;
import com.example.news.dao.impl.UserDaoImpl;
import com.example.news.model.User;

public class UserDaoTest {

    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl();

        // 1. 测试查询（先确保数据库里有这条数据）
        User u1 = userDao.findByUsername("admin");
        System.out.println(u1);

        // 2. 测试注册
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setPassword("123456");

        int rows = userDao.save(newUser);
        System.out.println("insert rows = " + rows);

        // 3. 再查一次
        User u2 = userDao.findByUsername("testuser");
        System.out.println(u2);
    }
}