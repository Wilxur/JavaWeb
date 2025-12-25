package com.example.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // 根据类别获取商品
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM products WHERE category = ? AND status = 'active' ORDER BY sales DESC, rating DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setStock(rs.getInt("stock"));
                product.setSales(rs.getInt("sales"));
                product.setRating(rs.getDouble("rating"));
                product.setBrand(rs.getString("brand"));

                // 处理标签
                String tagsStr = rs.getString("tags");
                if (tagsStr != null && !tagsStr.isEmpty()) {
                    product.setTags(tagsStr.split(","));
                }

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return products;
    }

    // 根据ID获取商品详情
    public Product getProductById(int id) {
        Product product = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM products WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setStock(rs.getInt("stock"));
                product.setSales(rs.getInt("sales"));
                product.setRating(rs.getDouble("rating"));
                product.setBrand(rs.getString("brand"));

                String tagsStr = rs.getString("tags");
                if (tagsStr != null && !tagsStr.isEmpty()) {
                    product.setTags(tagsStr.split(","));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return product;
    }

    // 搜索商品
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM products WHERE (name LIKE ? OR description LIKE ? OR brand LIKE ?) AND status = 'active'";
            pstmt = conn.prepareStatement(sql);
            String searchParam = "%" + keyword + "%";
            pstmt.setString(1, searchParam);
            pstmt.setString(2, searchParam);
            pstmt.setString(3, searchParam);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setStock(rs.getInt("stock"));
                product.setSales(rs.getInt("sales"));
                product.setRating(rs.getDouble("rating"));
                product.setBrand(rs.getString("brand"));

                String tagsStr = rs.getString("tags");
                if (tagsStr != null && !tagsStr.isEmpty()) {
                    product.setTags(tagsStr.split(","));
                }

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return products;
    }
}