package com.example.news.test;

import com.example.news.util.DBUtil;

import java.sql.Connection;

public class DruidConnectionTest {

    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null) {
                System.out.println("Druid 获取连接成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}