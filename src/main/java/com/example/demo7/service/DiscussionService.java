// service/DiscussionService.java
package com.example.demo7.service;

import com.example.demo7.model.Discussion;
import com.example.demo7.model.Reply;
import com.example.demo7.model.User;
import com.example.demo7.repository.DiscussionRepository;
import com.example.demo7.repository.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public Page<Discussion> getAllDiscussions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        return discussionRepository.findAllByOrderByCreateTimeDesc(pageable);
    }

    @Transactional(readOnly = true)
    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Discussion> getDiscussionById(Long id) {
        return discussionRepository.findById(id);
    }

    @Transactional
    public Discussion createDiscussion(String title, String content, User author) {
        Discussion discussion = new Discussion();
        discussion.setTitle(title);
        discussion.setContent(content);
        discussion.setAuthor(author);
        return discussionRepository.save(discussion);
    }

    @Transactional
    public Reply addReply(Long discussionId, String content, User author) {
        Optional<Discussion> discussionOpt = discussionRepository.findById(discussionId);
        if (discussionOpt.isEmpty()) {
            throw new IllegalArgumentException("讨论不存在");
        }

        Reply reply = new Reply();
        reply.setContent(content);
        reply.setAuthor(author);
        reply.setDiscussion(discussionOpt.get());

        return replyRepository.save(reply);
    }

    @Transactional(readOnly = true)
    public List<Discussion> searchDiscussions(String keyword) {
        return discussionRepository.searchByKeyword(keyword);
    }

    @Transactional(readOnly = true)
    public long getDiscussionCountByUser(User user) {
        return discussionRepository.countByAuthor(user);
    }

    @Transactional(readOnly = true)
    public List<Discussion> getUserDiscussions(User user) {
        return discussionRepository.findAllByAuthorOrderByCreateTimeDesc(user);
    }
}