package com.example.demo7.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Table(name = "replies")
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Discussion discussion;

    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

    // 设置作者（辅助方法）
    public void setAuthor(User author) {
        this.author = author;
        if (author != null && !author.getReplies().contains(this)) {
            author.getReplies().add(this);
        }
    }

    // 设置讨论（辅助方法）
    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
        if (discussion != null && !discussion.getReplies().contains(this)) {
            discussion.getReplies().add(this);
        }
    }
}