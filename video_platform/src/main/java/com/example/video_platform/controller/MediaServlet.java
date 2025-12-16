package com.example.video_platform.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;

@WebServlet("/media")
public class MediaServlet extends HttpServlet {

    private static final String BASE_DIR = "/data/video_uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = req.getParameter("path");
        if (fileName == null || fileName.isBlank()) {
            resp.sendError(400);
            return;
        }

        File f = new File(BASE_DIR, fileName);
        if (!f.exists()) {
            resp.sendError(404);
            return;
        }

        String mime = Files.probeContentType(f.toPath());
        if (mime == null) mime = "video/mp4";

        resp.setContentType(mime);
        resp.setHeader("Accept-Ranges", "bytes");

        long len = f.length();
        String range = req.getHeader("Range");

        try (RandomAccessFile raf = new RandomAccessFile(f, "r");
             OutputStream out = resp.getOutputStream()) {

            if (range != null && range.startsWith("bytes=")) {
                long start = Long.parseLong(range.replace("bytes=", "").split("-")[0]);
                long end = len - 1;
                long contentLen = end - start + 1;

                resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                resp.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + len);
                resp.setHeader("Content-Length", String.valueOf(contentLen));

                raf.seek(start);
                byte[] buf = new byte[8192];
                int r;
                while ((r = raf.read(buf)) != -1) out.write(buf, 0, r);
            } else {
                resp.setHeader("Content-Length", String.valueOf(len));
                byte[] buf = new byte[8192];
                int r;
                while ((r = raf.read(buf)) != -1) out.write(buf, 0, r);
            }
        }
    }
}
