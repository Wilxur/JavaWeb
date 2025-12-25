package com.example.video_platform.controller;

import com.example.video_platform.model.Category;
import com.example.video_platform.service.CategoryService;
import com.example.video_platform.service.impl.CategoryServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class UploadPageServlet extends HttpServlet {

    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Category> categories = categoryService.listAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/jsp/upload.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500);
        }
    }
}
