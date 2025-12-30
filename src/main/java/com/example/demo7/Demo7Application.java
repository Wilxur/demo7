package com.example.demo7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo7Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo7Application.class, args);
        System.out.println("应用启动成功！访问地址：http://localhost:8081");
        System.out.println("管理员账户：admin / 123456");
    }
}