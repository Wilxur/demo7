// controller/RegisterController.java
package com.example.demo7.controller;

import com.example.demo7.model.User;
import com.example.demo7.service.CaptchaService;
import com.example.demo7.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String email,
            @RequestParam String captcha,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // 验证码验证
        if (!captchaService.validateCaptcha(session, captcha)) {
            redirectAttributes.addFlashAttribute("msg", "验证码错误！");
            return "redirect:/register";
        }

        // 密码确认
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("msg", "两次输入的密码不一致！");
            return "redirect:/register";
        }

        // 用户名检查
        if (userService.isUsernameExists(username)) {
            redirectAttributes.addFlashAttribute("msg", "用户名已存在！");
            return "redirect:/register";
        }

        // 邮箱检查
        if (userService.isEmailExists(email)) {
            redirectAttributes.addFlashAttribute("msg", "邮箱已存在！");
            return "redirect:/register";
        }

        // 创建用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        try {
            userService.register(user);
            redirectAttributes.addFlashAttribute("msg", "注册成功！请登录。");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "注册失败：" + e.getMessage());
            return "redirect:/register";
        }
    }
}