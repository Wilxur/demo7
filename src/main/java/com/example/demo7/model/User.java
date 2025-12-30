package com.example.demo7.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "register_time")
    private LocalDateTime registerTime = LocalDateTime.now();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createTime DESC")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Discussion> discussions = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createTime DESC")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reply> replies = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        registerTime = LocalDateTime.now();
    }

    // 添加讨论（辅助方法）
    public void addDiscussion(Discussion discussion) {
        discussions.add(discussion);
        discussion.setAuthor(this);
    }

    // 添加回复（辅助方法）
    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setAuthor(this);
    }

    // 获取讨论数量
    @Transient
    public int getDiscussionCount() {
        return discussions != null ? discussions.size() : 0;
    }

    // 获取总回复数
    @Transient
    public int getTotalReplies() {
        return replies != null ? replies.size() : 0;
    }
}