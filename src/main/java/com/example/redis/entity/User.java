package com.example.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体类
 * 实现Serializable接口，以便于序列化存储到Redis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // 用户ID
    private Long id;
    
    // 用户名
    private String username;
    
    // 用户年龄
    private Integer age;
    
    // 用户邮箱
    private String email;
}
