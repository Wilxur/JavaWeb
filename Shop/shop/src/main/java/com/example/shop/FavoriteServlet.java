package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {

    private FavoriteDAO favoriteDAO = new FavoriteDAO();

    /* AJAX 入口：/favorite  POST 参数 action=add|remove&productId=xx  返回 JSON */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            resp.getWriter().write("{\"success\":false,\"msg\":\"未登录\"}");
            return;
        }
        User user = (User) session.getAttribute("user");

        String action = req.getParameter("action");
        int productId;
        try {
            productId = Integer.parseInt(req.getParameter("productId"));
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"success\":false,\"msg\":\"参数错误\"}");
            return;
        }

        boolean ok;
        if ("add".equals(action)) {
            ok = favoriteDAO.add(user.getId(), productId);
        } else if ("remove".equals(action)) {
            ok = favoriteDAO.remove(user.getId(), productId);
        } else {
            resp.getWriter().write("{\"success\":false,\"msg\":\"未知操作\"}");
            return;
        }

        resp.getWriter().write("{\"success\":" + ok + "}");
    }

    /* 进入收藏夹列表页 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        req.setAttribute("favorites", favoriteDAO.listByUser(user.getId()));
        req.getRequestDispatcher("/favorite.jsp").forward(req, resp);
    }
}