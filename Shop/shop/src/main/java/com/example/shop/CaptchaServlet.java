package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {

    private static final String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 设置字体
        g.setFont(new Font("Arial", Font.BOLD, 24));

        // 生成随机验证码
        Random random = new Random();
        StringBuilder captchaCode = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            char c = chars.charAt(random.nextInt(chars.length()));
            captchaCode.append(c);

            g.setColor(new Color(
                    random.nextInt(150),
                    random.nextInt(150),
                    random.nextInt(150)
            ));

            g.drawString(String.valueOf(c),
                    20 + i * 25 + random.nextInt(5),
                    28 + random.nextInt(5));
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(
                    random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255)
            ));
            g.drawLine(
                    random.nextInt(WIDTH),
                    random.nextInt(HEIGHT),
                    random.nextInt(WIDTH),
                    random.nextInt(HEIGHT)
            );
        }

        // 添加噪点
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            image.setRGB(x, y, random.nextInt(255));
        }

        g.dispose();

        // 将验证码存入session
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaCode.toString());
        session.setAttribute("captchaTime", System.currentTimeMillis());

        // 输出图像
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
}