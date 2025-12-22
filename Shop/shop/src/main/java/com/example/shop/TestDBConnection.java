package com.example.shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection();
            System.out.println("数据库连接成功！");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next()) {
                System.out.println("用户表中有 " + rs.getInt(1) + " 条记录");
            }

            DBUtil.close(conn, stmt, rs);
        } catch (Exception e) {
            System.out.println("数据库连接失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}