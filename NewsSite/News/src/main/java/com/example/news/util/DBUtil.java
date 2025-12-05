package com.example.news.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库连接工具类（纯手写 JDBC）
 */
public class DBUtil {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    // 静态代码块：加载配置文件
    static {
        try {
            Properties props = new Properties();
            InputStream is = DBUtil.class.getClassLoader()
                    .getResourceAsStream("db.properties");

            if (is == null) {
                throw new RuntimeException("找不到 db.properties 配置文件！");
            }

            props.load(is);
            is.close();

            driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");

            Class.forName(driver);
            System.out.println("✅ 数据库驱动加载成功！");

        } catch (Exception e) {
            System.err.println("❌ 数据库配置加载失败：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("数据库初始化失败", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("❌ 数据库连接失败：" + e.getMessage());
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

    /**
     * 关闭资源
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn, PreparedStatement ps) {
        close(conn, ps, null);
    }

    // 测试方法
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("🎉 数据库连接测试成功！");
            close(conn, null);
        }
    }
}