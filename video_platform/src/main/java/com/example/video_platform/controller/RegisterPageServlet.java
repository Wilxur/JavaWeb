package com.example.video_platform.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/registerPage")
public class RegisterPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500);
        }
    }
}
