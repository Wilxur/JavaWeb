package com.example.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // 获取用户的购物车商品列表
    public List<Cart> getCartByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT c.*, p.name, p.category, p.description, p.price, " +
                    "p.original_price, p.image_url, p.stock, p.sales, p.rating, p.brand " +
                    "FROM carts c " +
                    "JOIN products p ON c.product_id = p.id " +
                    "WHERE c.user_id = ? " +
                    "ORDER BY c.created_at DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));

                // 设置商品信息
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
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

                cart.setProduct(product);
                cartItems.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return cartItems;
    }

    // 添加商品到购物车
    public boolean addToCart(Cart cart) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            // 先检查是否已经存在
            String checkSql = "SELECT id, quantity FROM carts WHERE user_id = ? AND product_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, cart.getUserId());
            pstmt.setInt(2, cart.getProductId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // 已存在，更新数量
                int existingId = rs.getInt("id");
                int existingQty = rs.getInt("quantity");
                int newQty = existingQty + cart.getQuantity();

                pstmt.close();
                String updateSql = "UPDATE carts SET quantity = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, newQty);
                pstmt.setInt(2, existingId);

                return pstmt.executeUpdate() > 0;
            } else {
                // 不存在，插入新记录
                pstmt.close();
                String insertSql = "INSERT INTO carts (user_id, product_id, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, cart.getUserId());
                pstmt.setInt(2, cart.getProductId());
                pstmt.setInt(3, cart.getQuantity());

                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    // 更新购物车商品数量
    public boolean updateCartItem(int cartId, int quantity) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE carts SET quantity = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    // 删除购物车商品
    public boolean deleteCartItem(int cartId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM carts WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cartId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    // 清空用户购物车
    public boolean clearCart(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM carts WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    // 获取购物车商品总数
    public int getCartItemCount(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT SUM(quantity) as total_count FROM carts WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return 0;
    }
}