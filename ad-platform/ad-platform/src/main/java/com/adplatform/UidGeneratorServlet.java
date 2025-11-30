package com.adplatform;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import com.adplatform.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/api/get-uid")
public class UidGeneratorServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UidGeneratorServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String uid = null;

        // 1. 尝试从Cookie读取
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ad_platform_uid".equals(cookie.getName())) {
                    uid = cookie.getValue();
                    break;
                }
            }
        }

        // 2. 没有则生成新UID
        if (uid == null) {
            uid = UUID.randomUUID().toString().replace("-", "");

            // 存入Cookie（跨站需设置Domain和Path）
            Cookie uidCookie = new Cookie("ad_platform_uid", uid);
            uidCookie.setPath("/");
            uidCookie.setMaxAge(60 * 60 * 24 * 30); // 30天
            uidCookie.setHttpOnly(true);
            // uidCookie.setDomain(".yourdomain.com"); // 部署后设置你的域名
            resp.addCookie(uidCookie);

            // 同时存入数据库
            try (Connection conn = DBUtil.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT IGNORE INTO anonymous_users (uid) VALUES (?)"
                );
                ps.setString(1, uid);
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.error("数据库插入失败，UID: {}", uid, e);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"系统错误\"}");
                return;
            }
        }

        // 3. 返回JSON
        resp.getWriter().write("{\"uid\":\"" + uid + "\"}");
    }
}