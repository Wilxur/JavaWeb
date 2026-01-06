package com.example.shop;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        Map<String, Object> json = new HashMap<>();
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            json.put("success", false);
            json.put("message", "未登录");
            gson.toJson(json, resp.getWriter());
            return;
        }

        User user = (User) session.getAttribute("user");
        String type = req.getParameter("type");

        try {
            switch (type) {
                case "username":
                    String newName = req.getParameter("newUsername").trim();
                    if (newName.length() < 3 || newName.length() > 20) {
                        json.put("success", false);
                        json.put("message", "用户名3-20字符");
                    } else if (userDAO.usernameExists(newName)) {
                        json.put("success", false);
                        json.put("message", "用户名已存在");
                    } else {
                        user.setUsername(newName);
                        boolean ok = userDAO.update(user);
                        if (ok) {
                            session.setAttribute("user", user);
                            json.put("success", true);
                            json.put("newUsername", newName);
                        } else {
                            json.put("success", false);
                            json.put("message", "数据库错误");
                        }
                    }
                    break;

                case "password":
                    String oldPwd = req.getParameter("currentPassword");
                    String newPwd = req.getParameter("newPassword");
                    if (!user.getPassword().equals(oldPwd)) {
                        json.put("success", false);
                        json.put("message", "原密码错误");
                    } else {
                        user.setPassword(newPwd);
                        boolean ok = userDAO.updatePassword(user.getId(), newPwd);
                        json.put("success", ok);
                        if (ok) json.put("message", "密码已更新，下次登录请用新密码");
                    }
                    break;

                case "phone":
                    String newPhone = req.getParameter("newPhone");
                    user.setPhone(newPhone);
                    boolean ok = userDAO.update(user);
                    json.put("success", ok);
                    if (ok) json.put("newPhone", newPhone);
                    break;

                case "email":
                    String newEmail = req.getParameter("newEmail");
                    user.setEmail(newEmail);
                    ok = userDAO.update(user);
                    json.put("success", ok);
                    if (ok) json.put("newEmail", newEmail);
                    break;

                default:
                    json.put("success", false);
                    json.put("message", "未知类型");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("message", "系统异常");
            e.printStackTrace();
        }

        gson.toJson(json, resp.getWriter());
    }
}