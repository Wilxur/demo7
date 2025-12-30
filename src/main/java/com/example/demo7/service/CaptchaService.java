// service/CaptchaService.java
package com.example.demo7.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
@Slf4j
public class CaptchaService {

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int WIDTH = 110;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;

    public CaptchaResult generateCaptcha() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        Random random = new Random();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 生成验证码
        StringBuilder code = new StringBuilder();
        g.setFont(new Font("Arial", Font.BOLD, 26));

        for (int i = 0; i < CODE_LENGTH; i++) {
            char ch = CHARS.charAt(random.nextInt(CHARS.length()));
            code.append(ch);
            g.setColor(new Color(
                    random.nextInt(150),
                    random.nextInt(150),
                    random.nextInt(150)
            ));
            g.drawString(String.valueOf(ch), 15 + i * 22, 30);
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(
                    random.nextInt(150),
                    random.nextInt(150),
                    random.nextInt(150)
            ));
            g.drawLine(
                    random.nextInt(WIDTH),
                    random.nextInt(HEIGHT),
                    random.nextInt(WIDTH),
                    random.nextInt(HEIGHT)
            );
        }

        g.dispose();

        return new CaptchaResult(image, code.toString());
    }

    public boolean validateCaptcha(HttpSession session, String userInput) {
        String captcha = (String) session.getAttribute("CAPTCHA");
        if (captcha == null) {
            return false;
        }

        boolean isValid = captcha.equalsIgnoreCase(userInput);
        if (isValid) {
            session.removeAttribute("CAPTCHA");
        }
        return isValid;
    }

    public static class CaptchaResult {
        private final BufferedImage image;
        private final String code;

        public CaptchaResult(BufferedImage image, String code) {
            this.image = image;
            this.code = code;
        }

        public BufferedImage getImage() { return image; }
        public String getCode() { return code; }
    }
}