package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Random;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 跳转到支付页面
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        Order order = orderDAO.getOrderById(orderId);

        // 验证订单属于当前用户
        if(order == null || order.getUserId() != user.getId()) {
            response.sendRedirect(request.getContextPath() + "/order");
            return;
        }

        request.setAttribute("order", order);
        request.getRequestDispatcher("/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String paymentMethod = request.getParameter("paymentMethod");

        Order order = orderDAO.getOrderById(orderId);

        // 验证订单状态和用户
        if(order == null || order.getUserId() != user.getId() || !"pending".equals(order.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/order?action=view&id=" + orderId);
            return;
        }

        // 模拟支付处理
        boolean paymentSuccess = simulatePayment(order, paymentMethod);

        if(paymentSuccess) {
            // 更新订单状态为已支付
            orderDAO.updateOrderStatus(orderId, "paid");

            // ✅ 修改这里：添加 .jsp 后缀
            response.sendRedirect(request.getContextPath() + "/payment-result.jsp?orderId=" + orderId + "&result=success&amount=" + order.getTotalAmount());
        } else {
            // ✅ 修改这里：添加 .jsp 后缀
            response.sendRedirect(request.getContextPath() + "/payment-result.jsp?orderId=" + orderId + "&result=fail&amount=" + order.getTotalAmount());
        }
    }
    /**
     * 模拟支付处理（实际项目中调用真实支付接口）
     */
    private boolean simulatePayment(Order order, String paymentMethod) {
        // 模拟支付处理时间
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 模拟支付成功率 95%
        return random.nextDouble() < 0.95;
    }
}