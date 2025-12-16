package com.example.video_platform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Files;

@WebServlet("/media")
public class MediaServlet extends HttpServlet {

    private static final String VIDEO_BASE_DIR = "E:/video_uploads";
    // Linux 服务器示例：/data/video_uploads

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fileName = req.getParameter("path");
        if (fileName == null || fileName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        File videoFile = new File(VIDEO_BASE_DIR, fileName);
        if (!videoFile.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = Files.probeContentType(videoFile.toPath());
        if (mimeType == null) {
            mimeType = "video/mp4";
        }

        resp.setContentType(mimeType);
        resp.setHeader("Accept-Ranges", "bytes");

        long fileLength = videoFile.length();
        String range = req.getHeader("Range");

        try (RandomAccessFile raf = new RandomAccessFile(videoFile, "r");
             OutputStream out = resp.getOutputStream()) {

            if (range != null) {
                // Range: bytes=start-end
                long start = Long.parseLong(range.replaceAll("bytes=", "")
                        .split("-")[0]);
                long end = fileLength - 1;

                long contentLength = end - start + 1;

                resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                resp.setHeader("Content-Range",
                        "bytes " + start + "-" + end + "/" + fileLength);
                resp.setHeader("Content-Length", String.valueOf(contentLength));

                raf.seek(start);

                byte[] buffer = new byte[8192];
                int len;
                while ((len = raf.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }

            } else {
                resp.setHeader("Content-Length", String.valueOf(fileLength));

                byte[] buffer = new byte[8192];
                int len;
                while ((len = raf.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
        }
    }
}
