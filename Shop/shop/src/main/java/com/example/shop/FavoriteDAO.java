package com.example.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    /* 查询用户全部收藏（带商品信息） */
    public List<Favorite> listByUser(int userId) {
        List<Favorite> list = new ArrayList<>();
        String sql = "SELECT f.id, f.product_id, p.name, p.price, p.image_url, p.brand " +
                "FROM favorites f JOIN products p ON f.product_id = p.id " +
                "WHERE f.user_id = ? ORDER BY f.id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Favorite f = new Favorite();
                f.setId(rs.getInt("id"));
                f.setUserId(userId);
                f.setProductId(rs.getInt("product_id"));

                Product p = new Product();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                p.setBrand(rs.getString("brand"));
                f.setProduct(p);
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /* 判断是否已收藏 */
    public boolean exists(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id=? AND product_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* 添加收藏 */
    public boolean add(int userId, int productId) {
        if (exists(userId, productId)) return false;
        String sql = "INSERT INTO favorites (user_id, product_id) VALUES (?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* 取消收藏 */
    public boolean remove(int userId, int productId) {
        String sql = "DELETE FROM favorites WHERE user_id=? AND product_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}