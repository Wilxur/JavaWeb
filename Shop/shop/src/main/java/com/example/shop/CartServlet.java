package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // 如果是从商品列表页添加商品，单独处理
        if ("add".equals(action)) {
            handleAddFromProductList(request, response);
            return;
        }

        // 否则显示购物车页面
        showCartPage(request, response);
    }

    /**
     * 处理从商品列表页添加商品（通过GET请求）
     */
    private void handleAddFromProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String category = request.getParameter("category"); // 获取商品类别

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            session.setAttribute("error", "商品不存在！");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        if (product.getStock() < quantity) {
            session.setAttribute("error", "库存不足，当前库存：" + product.getStock());
            response.sendRedirect(request.getContextPath() + "/products.jsp?category=" + category);
            return;
        }

        Cart cart = new Cart(user.getId(), productId, quantity);
        boolean success = cartDAO.addToCart(cart);

        if (success) {
            session.setAttribute("message", "商品已添加到购物车！");
        } else {
            session.setAttribute("error", "添加到购物车失败，请重试！");
        }

        // 重定向回商品列表页
        response.sendRedirect(request.getContextPath() + "/products.jsp?category=" + category);
    }

    /**
     * 显示购物车页面
     */
    private void showCartPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Cart> cartItems = cartDAO.getCartByUserId(user.getId());

        double totalAmount = 0;
        int totalQuantity = 0;

        for (Cart item : cartItems) {
            totalAmount += item.getSubtotal();
            totalQuantity += item.getQuantity();
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("totalQuantity", totalQuantity);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
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

        if ("add".equals(action)) {
            // POST方式的添加（保留原有逻辑）
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product product = productDAO.getProductById(productId);
            if (product == null) {
                request.setAttribute("error", "商品不存在");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
            }

            if (product.getStock() < quantity) {
                request.setAttribute("error", "库存不足，当前库存：" + product.getStock());
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
            }

            Cart cart = new Cart(user.getId(), productId, quantity);
            boolean success = cartDAO.addToCart(cart);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/cart");
            } else {
                request.setAttribute("error", "添加到购物车失败");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
            }

        } else if ("update".equals(action)) {
            // 更新购物车商品数量
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            if (quantity <= 0) {
                cartDAO.deleteCartItem(cartId);
            } else {
                cartDAO.updateCartItem(cartId, quantity);
            }
            response.sendRedirect(request.getContextPath() + "/cart");

        } else if ("remove".equals(action)) {
            // 移除购物车商品
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            cartDAO.deleteCartItem(cartId);
            response.sendRedirect(request.getContextPath() + "/cart");

        } else if ("clear".equals(action)) {
            // 清空购物车
            cartDAO.clearCart(user.getId());
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}