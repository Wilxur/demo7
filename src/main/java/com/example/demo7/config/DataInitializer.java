package com.example.demo7.config;

import com.example.demo7.model.Discussion;
import com.example.demo7.model.User;
import com.example.demo7.repository.DiscussionRepository;
import com.example.demo7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public void run(String... args) throws Exception {
        // 初始化管理员用户
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setEmail("admin@example.com");
            userRepository.save(admin);

            // 创建示例讨论1
            Discussion discussion1 = new Discussion();
            discussion1.setTitle("欢迎来到问答平台！");
            discussion1.setContent("这是一个示例讨论，欢迎大家参与！");
            discussion1.setAuthor(admin);
            discussion1.setCreateTime(LocalDateTime.now().minusDays(1));
            discussionRepository.save(discussion1);

            // 创建示例讨论2
            Discussion discussion2 = new Discussion();
            discussion2.setTitle("Spring Boot开发问题");
            discussion2.setContent("在Spring Boot中如何配置多数据源？");
            discussion2.setAuthor(admin);
            discussion2.setCreateTime(LocalDateTime.now());
            discussionRepository.save(discussion2);

            // 创建示例用户
            User user1 = new User();
            user1.setUsername("user1");
            user1.setPassword("123456");
            user1.setEmail("user1@example.com");
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("user2");
            user2.setPassword("123456");
            user2.setEmail("user2@example.com");
            userRepository.save(user2);

            System.out.println("数据初始化完成！");
            System.out.println("管理员账户: admin / 123456");
            System.out.println("普通用户: user1 / 123456");
            System.out.println("普通用户: user2 / 123456");
        }
    }
}