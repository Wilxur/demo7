// controller/DiscussionController.java
package com.example.demo7.controller;

import com.example.demo7.model.Discussion;
import com.example.demo7.model.User;
import com.example.demo7.service.DiscussionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@Slf4j
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @GetMapping("/discussions")
    public String listDiscussions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Page<Discussion> discussions = discussionService.getAllDiscussions(page, size);
        model.addAttribute("discussions", discussions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", discussions.getTotalPages());
        return "discussionList";
    }

    @GetMapping("/discussions/list-fragment")
    public String listFragment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Discussion> discussions = discussionService.getAllDiscussions(page, size);
        model.addAttribute("discussions", discussions);
        return "fragments/discussionListFragment :: discussionList";
    }

    @GetMapping("/discussions/{id}")
    public String viewDiscussion(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Optional<Discussion> discussionOpt = discussionService.getDiscussionById(id);
        if (discussionOpt.isEmpty()) {
            return "redirect:/discussions";
        }

        model.addAttribute("discussion", discussionOpt.get());
        return "discussionDetail";
    }

    @GetMapping("/discussions/new")
    public String newDiscussionPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "newDiscussion";
    }

    @PostMapping("/discussions")
    public String createDiscussion(
            @RequestParam String title,
            @RequestParam String content,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "标题和内容都不能为空！");
            return "redirect:/discussions/new";
        }

        if (title.length() < 3) {
            redirectAttributes.addFlashAttribute("msg", "标题至少需要3个字符！");
            return "redirect:/discussions/new";
        }

        try {
            Discussion discussion = discussionService.createDiscussion(title, content, user);
            redirectAttributes.addFlashAttribute("msg", "讨论创建成功！");
            return "redirect:/discussions/" + discussion.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "创建失败：" + e.getMessage());
            return "redirect:/discussions/new";
        }
    }

    @GetMapping("/discussions/search")
    public String searchDiscussions(
            @RequestParam String keyword,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("discussions", discussionService.searchDiscussions(keyword));
        model.addAttribute("keyword", keyword);
        return "discussionList";
    }
}