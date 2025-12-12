package com.adplatform.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {
    private static final Properties props = new Properties();

    // 静态块加载配置和驱动
    static {
        try {
            // 加载db.properties文件
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (is == null) {
                throw new RuntimeException("db.properties文件未找到");
            }
            props.load(is);

            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL数据库驱动加载失败", e);
        } catch (IOException e) {
            throw new RuntimeException("db.properties配置文件读取失败", e);
        }
    }

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        if (url == null || username == null || password == null) {
            throw new RuntimeException("数据库配置缺失，请检查db.properties文件");
        }

        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 执行增删改操作
     * @param sql SQL语句（带?占位符）
     * @param params 可变参数，对应SQL中的?占位符
     * @return 影响的行数
     */
    public static int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            // 自动绑定参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SQL执行失败: " + sql, e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * 执行查询操作
     * @param sql SQL查询语句（带?占位符）
     * @param mapper 结果集映射器，将ResultSet映射为实体对象
     * @param params 可变参数，对应SQL中的?占位符
     * @param <T> 返回的实体类型
     * @return 查询结果列表
     */
    public static <T> List<T> executeQuery(String sql, RowMapper<T> mapper, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> result = new ArrayList<>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            // 自动绑定参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery();
            // 自动映射每一行数据
            while (rs.next()) {
                result.add(mapper.mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("查询失败: " + sql, e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * 函数式接口：定义如何将ResultSet映射成实体对象
     * @param <T> 实体类型
     */
    @FunctionalInterface
    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }

    /**
     * 关闭数据库资源
     * @param conn 连接对象
     * @param stmt 语句对象
     * @param rs 结果集对象
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}