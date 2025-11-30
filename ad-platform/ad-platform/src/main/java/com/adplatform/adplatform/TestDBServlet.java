package com.adplatform.adplatform;

import com.adplatform.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/test-db")
public class TestDBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null) {
                resp.getWriter().write("<h1>数据库连接成功！</h1>");
            }
        } catch (SQLException e) {
            resp.getWriter().write("<h1>数据库连接失败：" + e.getMessage() + "</h1>");
        }
    }
}