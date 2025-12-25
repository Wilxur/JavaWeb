package com.example.video_platform.dao.impl;

import com.example.video_platform.dao.CategoryDao;
import com.example.video_platform.model.Category;
import com.example.video_platform.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findAll() {
        String sql = "SELECT id, name FROM category ORDER BY id";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Category> list = new ArrayList<>();
            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getLong("id"));
                cat.setName(rs.getString("name"));
                list.add(cat);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
