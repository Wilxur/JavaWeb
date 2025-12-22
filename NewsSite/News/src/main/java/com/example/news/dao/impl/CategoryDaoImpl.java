package com.example.news.dao.impl;

import com.example.news.dao.CategoryDao;
import com.example.news.model.Category;
import com.example.news.util.DBUtil;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private static final String SQL_FIND_ALL =
            "SELECT id, name, created_at FROM category ORDER BY id";

    @Override
    public List<Category> findAll() {
        return DBUtil.executeQuery(
                SQL_FIND_ALL,
                rs -> {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setCreatedAt(rs.getTimestamp("created_at"));
                    return c;
                }
        );
    }
}