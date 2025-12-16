package com.example.video_platform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * 视频详情 / 播放控制器
 */
@WebServlet("/video")
public class VideoDetailServlet extends HttpServlet {

    // 当前测试用的视频文件名（已上传到 E:/video_uploads）
    private static final String TEST_VIDEO_FILE =
            "182eb616-45ee-4940-84b0-cbcd97a288c0_04d4063bd8b12702792b6474dc349c31.mp4";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("videos");
            return;
        }

        long id = Long.parseLong(idStr);

        // 构造视频信息（后续将由 VideoService + DAO 提供）
        Map<String, Object> video = Map.of(
                "id", id,
                "title", "测试视频播放",
                "videoUrl", "media?path=" + TEST_VIDEO_FILE
        );

        req.setAttribute("video", video);
        req.getRequestDispatcher("/jsp/video.jsp").forward(req, resp);
    }
}
