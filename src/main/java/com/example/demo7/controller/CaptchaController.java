// controller/CaptchaController.java
package com.example.demo7.controller;

import com.example.demo7.service.CaptchaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.imageio.ImageIO;
import java.io.OutputStream;

@Controller
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/captcha")
    public void captchaImage(HttpSession session, HttpServletResponse response) throws Exception {
        CaptchaService.CaptchaResult captchaResult = captchaService.generateCaptcha();

        // 存储验证码到session
        session.setAttribute("CAPTCHA", captchaResult.getCode());

        // 设置响应头
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 输出图像
        try (OutputStream os = response.getOutputStream()) {
            ImageIO.write(captchaResult.getImage(), "png", os);
            os.flush();
        }
    }
}