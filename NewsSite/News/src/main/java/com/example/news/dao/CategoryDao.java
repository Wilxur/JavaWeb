package com.example.news.dao;

import com.example.news.model.Category;
import com.example.news.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    /**
     * 查询全部分类
     */
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();

        String sql = "SELECT category_id, category_name, description, created_at FROM category ORDER BY category_id";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setDescription(rs.getString("description"));
                c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return list;
    }

    /**
     * 根据 ID 查询
     */
    public Category findById(int id) {
        String sql = "SELECT * FROM category WHERE category_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Category c = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setDescription(rs.getString("description"));
                c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return c;
    }
}