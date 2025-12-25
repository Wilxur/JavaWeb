package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检查登录状态
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 获取类别参数
        String category = request.getParameter("category");
        if (category == null || category.isEmpty()) {
            category = "electronics"; // 默认显示电子产品
        }

        // 设置类别显示名称
        Map<String, String> categoryNames = new HashMap<>();
        categoryNames.put("electronics", "电子产品");
        categoryNames.put("clothing", "服装鞋帽");
        categoryNames.put("food", "食品饮料");
        categoryNames.put("beauty", "美妆护肤");
        categoryNames.put("home", "家居用品");
        categoryNames.put("sports", "运动户外");
        String categoryName = categoryNames.get(category);

        // 从数据库获取商品数据
        List<Product> products = productDAO.getProductsByCategory(category);

        // 计算总销量和平均评分
        int totalSales = 0;
        double totalRating = 0.0;
        for (Product product : products) {
            totalSales += product.getSales();
            totalRating += product.getRating();
        }
        double averageRating = products.size() > 0 ? totalRating / products.size() : 0;

        // 设置请求属性
        request.setAttribute("products", products);
        request.setAttribute("category", category);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("totalSales", totalSales);
        request.setAttribute("averageRating", averageRating);

        // 转发到 products.jsp
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}