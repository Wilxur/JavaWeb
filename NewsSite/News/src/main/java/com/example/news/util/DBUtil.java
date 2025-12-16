package com.example.news.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * Druid 数据库连接池工具类
 * 用于服务器部署，性能稳定，线程安全
 */
public class DBUtil {

    private static DataSource dataSource;

    // 静态代码块在项目启动时只执行一次
    static {
        try {
            Properties properties = new Properties();
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载数据库配置失败，请检查 db.properties");
        }
    }

    /**
     * 获取数据库连接（从连接池中取，不需要手动创建）
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 统一关闭资源（连接池回收连接）
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {

        try {
            if (rs != null) rs.close();
        } catch (Exception ignored) {}

        try {
            if (stmt != null) stmt.close();
        } catch (Exception ignored) {}

        try {
            if (conn != null) conn.close();  // 归还连接池
        } catch (Exception ignored) {}
    }
}