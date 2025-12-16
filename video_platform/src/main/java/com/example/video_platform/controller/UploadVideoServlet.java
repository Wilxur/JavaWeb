package com.example.video_platform.controller;

import com.example.video_platform.model.User;
import com.example.video_platform.model.Video;
import com.example.video_platform.service.VideoService;
import com.example.video_platform.service.impl.VideoServiceImpl;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/uploadVideo")
@MultipartConfig(maxFileSize = 1024L * 1024 * 300) // 300MB
public class UploadVideoServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "/data/video_uploads";
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");

            // 要求登录后上传（最简单实现）
            User user = (User) req.getSession().getAttribute("user");
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login?error=please_login");
                return;
            }

            String title = req.getParameter("title");
            long categoryId = Long.parseLong(req.getParameter("categoryId"));
            Part part = req.getPart("videoFile");
            if (part == null || part.getSize() == 0) {
                resp.sendRedirect(req.getContextPath() + "/upload?error=no_file");
                return;
            }

            String original = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String newName = UUID.randomUUID() + "_" + original;

            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            File saved = new File(dir, newName);
            part.write(saved.getAbsolutePath());

            Video v = new Video();
            v.setTitle(title);
            v.setFilePath(newName);          // 存文件名
            v.setCategoryId(categoryId);
            v.setUploaderId(user.getId());

            videoService.upload(v);

            resp.sendRedirect(req.getContextPath() + "/videos");
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
