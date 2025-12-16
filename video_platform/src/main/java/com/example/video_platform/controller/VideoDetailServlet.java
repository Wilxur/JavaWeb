package com.example.video_platform.controller;

import com.example.video_platform.model.Video;
import com.example.video_platform.service.VideoService;
import com.example.video_platform.service.impl.VideoServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/video")
public class VideoDetailServlet extends HttpServlet {

    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isBlank()) {
                resp.sendRedirect(req.getContextPath() + "/videos");
                return;
            }
            long id = Long.parseLong(idStr);

            Video v = videoService.getById(id);
            if (v == null) {
                resp.sendRedirect(req.getContextPath() + "/videos");
                return;
            }

            // videoUrl 指向媒体流接口
            req.setAttribute("video", v);
            req.getRequestDispatcher("/jsp/video.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
