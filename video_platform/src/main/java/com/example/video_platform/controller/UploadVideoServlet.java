package com.example.video_platform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/uploadVideo")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1MB
        maxFileSize = 1024 * 1024 * 100,       // 100MB
        maxRequestSize = 1024 * 1024 * 120     // 120MB
)
public class UploadVideoServlet extends HttpServlet {

    // ⚠️ 强烈建议使用项目外目录（Windows / Linux 通用）
    private static final String UPLOAD_DIR = "E:/video_uploads";
    // Linux 服务器可改为：/data/video_uploads

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        Part videoPart = req.getPart("videoFile");

        if (videoPart == null || videoPart.getSize() == 0) {
            resp.sendRedirect("upload");
            return;
        }

        // 原始文件名
        String originalFileName = Paths.get(videoPart.getSubmittedFileName())
                .getFileName().toString();

        // 生成唯一文件名，避免冲突
        String newFileName = UUID.randomUUID() + "_" + originalFileName;

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File savedFile = new File(uploadDir, newFileName);

        // 写入磁盘
        videoPart.write(savedFile.getAbsolutePath());

        // TODO（下一步）：调用 VideoService 保存数据库记录
        // title, savedFile.getAbsolutePath(), uid, categoryId, uploadTime

        // 上传完成 → 返回视频列表
        resp.sendRedirect("videos");
    }
}
