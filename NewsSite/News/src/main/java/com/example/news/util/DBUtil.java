package com.example.news.util;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {

    private static final Properties props = new Properties();

    static {
        try {
            InputStream is = DBUtil.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");
            if (is == null) {
                throw new RuntimeException("db.properties 未找到");
            }
            props.load(is);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException("DBUtil 初始化失败", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password")
        );
    }

    public static int executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("更新失败：" + sql, e);
        }
    }

    public static <T> List<T> executeQuery(
            String sql,
            RowMapper<T> mapper,
            Object... params
    ) {
        List<T> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapper.mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("查询失败：" + sql, e);
        }

        return list;
    }

    @FunctionalInterface
    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}