// controller/LoginController.java
package com.example.demo7.controller;

import com.example.demo7.model.User;
import com.example.demo7.service.CaptchaService;
import com.example.demo7.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/discussions";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // 验证码验证
        if (!captchaService.validateCaptcha(session, captcha)) {
            redirectAttributes.addFlashAttribute("msg", "验证码错误！");
            return "redirect:/login";
        }

        // 用户验证
        if (!userService.validateLogin(username, password)) {
            redirectAttributes.addFlashAttribute("msg", "用户名或密码错误！");
            return "redirect:/login";
        }

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            userService.setLoginUser(session, userOpt.get());
            redirectAttributes.addFlashAttribute("msg", "登录成功！欢迎您，" + username);
            return "redirect:/success";
        }

        return "redirect:/login";
    }

    @GetMapping("/success")
    public String successPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", session.getAttribute("username"));
        return "success";
    }
}