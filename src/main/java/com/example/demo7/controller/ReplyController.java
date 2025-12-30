// controller/ReplyController.java
package com.example.demo7.controller;

import com.example.demo7.model.User;
import com.example.demo7.service.DiscussionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class ReplyController {

    @Autowired
    private DiscussionService discussionService;

    @PostMapping("/replies")
    public String addReply(
            @RequestParam Long discussionId,
            @RequestParam String content,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (content == null || content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "回复内容不能为空！");
            return "redirect:/discussions/" + discussionId;
        }

        try {
            discussionService.addReply(discussionId, content, user);
            redirectAttributes.addFlashAttribute("msg", "回复成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "回复失败：" + e.getMessage());
        }

        return "redirect:/discussions/" + discussionId;
    }
}