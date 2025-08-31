package com.example.redis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体类
 * 用于表示系统中的用户信息，实现了Serializable接口以便在Redis中序列化存储
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    
    /**
     * 序列化版本标识符
     * 用于确保序列化和反序列化过程中类版本的一致性
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     * 作为用户的唯一标识符
     */
    private Long id;
    
    /**
     * 用户名
     * 用户的名称信息
     */
    private String username;
    
    /**
     * 年龄
     * 用户的年龄信息
     */
    private Integer age;
    
    /**
     * 邮箱
     * 用户的邮箱地址
     */
    private String email;
    
}
