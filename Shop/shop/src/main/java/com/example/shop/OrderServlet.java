package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("view".equals(action)) {
            // 查看订单详情
            int orderId = Integer.parseInt(request.getParameter("id"));
            Order order = orderDAO.getOrderById(orderId);

            if (order == null || order.getUserId() != user.getId()) {
                response.sendRedirect(request.getContextPath() + "/order?action=list");
                return;
            }

            request.setAttribute("order", order);
            request.getRequestDispatcher("/order_detail.jsp").forward(request, response);

        } else if ("create".equals(action)) {
            // 创建订单页面
            // 这里可以从购物车获取商品
            List<Cart> cartItems = cartDAO.getCartByUserId(user.getId());

            if (cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            double totalAmount = 0;
            for (Cart item : cartItems) {
                totalAmount += item.getSubtotal();
            }

            request.setAttribute("cartItems", cartItems);
            request.setAttribute("totalAmount", totalAmount);
            request.getRequestDispatcher("/order_create.jsp").forward(request, response);

        } else {
            // 默认：订单列表
            List<Order> orders = orderDAO.getOrdersByUserId(user.getId());
            int[] stats = orderDAO.getOrderStats(user.getId());

            request.setAttribute("orders", orders);
            request.setAttribute("stats", stats);
            request.getRequestDispatcher("/orders.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // 创建订单
            String paymentMethod = request.getParameter("paymentMethod");
            String shippingAddress = request.getParameter("shippingAddress");
            String contactName = request.getParameter("contactName");
            String contactPhone = request.getParameter("contactPhone");
            String note = request.getParameter("note");

            // 获取购物车商品
            List<Cart> cartItems = cartDAO.getCartByUserId(user.getId());

            if (cartItems.isEmpty()) {
                request.setAttribute("error", "购物车为空，无法创建订单");
                request.getRequestDispatcher("/cart").forward(request, response);
                return;
            }

            // 计算总金额
            double totalAmount = 0;
            for (Cart item : cartItems) {
                totalAmount += item.getSubtotal();
            }

            // 创建订单
            Order order = new Order();
            order.setUserId(user.getId());
            order.setTotalAmount(totalAmount);
            order.setStatus("pending");
            order.setPaymentMethod(paymentMethod);
            order.setShippingAddress(shippingAddress);
            order.setContactName(contactName);
            order.setContactPhone(contactPhone);
            order.setNote(note);

            int orderId = orderDAO.createOrder(order);

            if (orderId > 0) {
                // 添加订单明细
                for (Cart cartItem : cartItems) {
                    Product product = cartItem.getProduct();
                    OrderItem orderItem = new OrderItem(
                            orderId,
                            cartItem.getProductId(),
                            product.getName(),
                            product.getImageUrl(),
                            product.getPrice(),
                            cartItem.getQuantity()
                    );
                    orderDAO.addOrderItem(orderItem);
                }

                // 清空购物车
                cartDAO.clearCart(user.getId());

                // 重定向到订单详情页面
                response.sendRedirect(request.getContextPath() + "/order?action=view&id=" + orderId);
            } else {
                request.setAttribute("error", "创建订单失败");
                request.getRequestDispatcher("/cart").forward(request, response);
            }

        } else if ("cancel".equals(action)) {
            // 取消订单
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = orderDAO.getOrderById(orderId);

            if (order != null && order.getUserId() == user.getId() && "pending".equals(order.getStatus())) {
                orderDAO.cancelOrder(orderId);
            }

            response.sendRedirect(request.getContextPath() + "/order");
        }
    }
}