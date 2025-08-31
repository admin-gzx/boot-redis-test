package com.example.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot应用启动类
 * 这是整个Spring Boot应用的入口点，负责启动Spring应用上下文
 */
@SpringBootApplication
public class SpringbootRedisExampleApplication {

    /**
     * 应用程序入口方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用，创建并初始化Spring应用上下文
        SpringApplication.run(SpringbootRedisExampleApplication.class, args);
    }

}
