package com.example.video_platform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL =
            "jdbc:mysql://10.100.164.34:3306/video_platform" +
                    "?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";

    private static final String USER = "root";
    private static final String PASSWORD = "162810Wang!";

    static {
        try {
            // MySQL 8 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver 加载失败", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
