package com.example.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;  // ✅ 添加日志导入
import org.slf4j.LoggerFactory;

public class OrderDAO {
    // ✅ 添加 logger 定义
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    // 生成订单号
    private String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // 创建订单
    public int createOrder(Order order) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO orders (order_number, user_id, total_amount, status, " +
                    "payment_method, shipping_address, contact_name, contact_phone, note) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String orderNumber = generateOrderNumber();
            pstmt.setString(1, orderNumber);
            pstmt.setInt(2, order.getUserId());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMethod());
            pstmt.setString(6, order.getShippingAddress());
            pstmt.setString(7, order.getContactName());
            pstmt.setString(8, order.getContactPhone());
            pstmt.setString(9, order.getNote());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("创建订单失败", e);  // ✅ 使用 logger
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return -1;
    }

    // 添加订单明细
    public boolean addOrderItem(OrderItem item) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO order_items (order_id, product_id, product_name, " +
                    "product_image, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, item.getOrderId());
            pstmt.setInt(2, item.getProductId());
            pstmt.setString(3, item.getProductName());
            pstmt.setString(4, item.getProductImage());
            pstmt.setDouble(5, item.getPrice());
            pstmt.setInt(6, item.getQuantity());
            pstmt.setDouble(7, item.getSubtotal());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("添加订单明细失败", e);  // ✅ 使用 logger
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    // 获取用户的所有订单
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setContactName(rs.getString("contact_name"));
                order.setContactPhone(rs.getString("contact_phone"));
                order.setNote(rs.getString("note"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));

                // 获取订单明细
                order.setItems(getOrderItemsByOrderId(order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error("获取用户订单失败 - 用户ID: {}", userId, e);  // ✅ 使用 logger
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return orders;
    }

    // 根据ID获取订单
    public Order getOrderById(int orderId) {
        Order order = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM orders WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setContactName(rs.getString("contact_name"));
                order.setContactPhone(rs.getString("contact_phone"));
                order.setNote(rs.getString("note"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));

                // 获取订单明细
                order.setItems(getOrderItemsByOrderId(orderId));

                // 获取用户信息
                UserDAO userDAO = new UserDAO();
                order.setUser(userDAO.findByUserId(order.getUserId()));
            }
        } catch (SQLException e) {
            logger.error("获取订单失败 - 订单ID: {}", orderId, e);  // ✅ 使用 logger
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return order;
    }

    // 根据订单号获取订单
    public Order getOrderByNumber(String orderNumber) {
        Order order = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM orders WHERE order_number = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orderNumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setContactName(rs.getString("contact_name"));
                order.setContactPhone(rs.getString("contact_phone"));
                order.setNote(rs.getString("note"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));

                // 获取订单明细
                order.setItems(getOrderItemsByOrderId(order.getId()));
            }
        } catch (SQLException e) {
            logger.error("获取订单失败 - 订单号: {}", orderNumber, e);  // ✅ 使用 logger
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return order;
    }

    // 获取订单明细
    private List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM order_items WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setProductImage(rs.getString("product_image"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setSubtotal(rs.getDouble("subtotal"));
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("获取订单明细失败 - 订单ID: {}", orderId, e);  // ✅ 使用 logger
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return items;
    }

    // 更新订单状态
    public boolean updateOrderStatus(int orderId, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE orders SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("更新订单状态失败 - 订单ID: {}, 状态: {}", orderId, status, e);  // ✅ 使用 logger
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    // 取消订单
    public boolean cancelOrder(int orderId) {
        return updateOrderStatus(orderId, "cancelled");
    }

    // 获取订单统计信息
    public int[] getOrderStats(int userId) {
        int[] stats = new int[5]; // pending, paid, shipped, delivered, cancelled

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT status, COUNT(*) as count FROM orders WHERE user_id = ? GROUP BY status";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");

                switch (status) {
                    case "pending": stats[0] = count; break;
                    case "paid": stats[1] = count; break;
                    case "shipped": stats[2] = count; break;
                    case "delivered": stats[3] = count; break;
                    case "cancelled": stats[4] = count; break;
                }
            }
        } catch (SQLException e) {
            logger.error("获取订单统计失败 - 用户ID: {}", userId, e);  // ✅ 使用 logger
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return stats;
    }
}