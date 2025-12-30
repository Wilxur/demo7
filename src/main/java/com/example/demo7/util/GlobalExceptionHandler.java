// util/GlobalExceptionHandler.java
package com.example.demo7.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletRequest request,
                                  RedirectAttributes redirectAttributes, Model model) {
        log.error("发生异常: ", ex);

        String errorMsg = "系统错误：" + ex.getMessage();

        // 如果是AJAX请求，返回错误信息
        String requestedWith = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestedWith)) {
            model.addAttribute("error", errorMsg);
            return "error/fragment :: error";
        }

        // 普通请求，重定向到首页
        redirectAttributes.addFlashAttribute("msg", errorMsg);

        // 检查用户是否登录
        Object user = request.getSession().getAttribute("user");
        if (user != null) {
            return "redirect:/discussions";
        } else {
            return "redirect:/login";
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex,
                                                 RedirectAttributes redirectAttributes) {
        log.warn("参数错误: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("msg", ex.getMessage());
        return "redirect:/discussions";
    }
}