package com.example.news.test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnectionTest {

    public static void main(String[] args) {

        // ⚠️ 请根据你的实际情况修改这 4 个参数
        String url = "jdbc:mysql://10.100.164.16:3306/newssite_db"
                + "?useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC"
                + "&characterEncoding=utf8";
        String username = "root";
        String password = "Mysql@2025";

        try {
            // MySQL 8.x 驱动（Tomcat 10 / Jakarta 推荐）
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, username, password);

            if (conn != null) {
                System.out.println("数据库连接成功");
                conn.close();
            }

        } catch (Exception e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
    }
}