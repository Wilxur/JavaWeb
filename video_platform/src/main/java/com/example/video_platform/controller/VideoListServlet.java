package com.example.video_platform.controller;

import com.example.video_platform.model.Category;
import com.example.video_platform.model.Video;
import com.example.video_platform.service.CategoryService;
import com.example.video_platform.service.VideoService;
import com.example.video_platform.service.impl.CategoryServiceImpl;
import com.example.video_platform.service.impl.VideoServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/videos")
public class VideoListServlet extends HttpServlet {

    private final VideoService videoService = new VideoServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String cid = req.getParameter("categoryId");
            Long categoryId = (cid == null || cid.isBlank()) ? null : Long.parseLong(cid);

            List<Video> videos = videoService.list(categoryId);
            List<Category> categories = categoryService.listAll();

            req.setAttribute("videos", videos);
            req.setAttribute("categories", categories);
            req.setAttribute("selectedCategoryId", categoryId);

            req.getRequestDispatcher("/jsp/videos.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
