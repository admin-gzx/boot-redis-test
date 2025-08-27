package com.example.redis.controller;

import com.example.redis.entity.User;
import com.example.redis.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 提供API接口，演示Redis的使用
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 保存用户到Redis
     */
    @PostMapping("/save")
    public Map<String, Object> saveUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = userService.saveUserToRedis(user);
            if (success) {
                result.put("code", 200);
                result.put("message", "用户保存到Redis成功");
                result.put("data", user);
            } else {
                result.put("code", 500);
                result.put("message", "用户保存到Redis失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 从Redis获取用户
     */
    @GetMapping("/get/{userId}")
    public Map<String, Object> getUser(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getUserFromRedis(userId);
            if (user != null) {
                result.put("code", 200);
                result.put("message", "从Redis获取用户成功");
                result.put("data", user);
            } else {
                result.put("code", 404);
                result.put("message", "Redis中未找到该用户");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 从Redis删除用户
     */
    @DeleteMapping("/delete/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.deleteUserFromRedis(userId);
            result.put("code", 200);
            result.put("message", "从Redis删除用户成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 演示Hash结构的使用 - 保存用户名
     */
    @PostMapping("/hash/save")
    public Map<String, Object> saveUserNameToHash(@RequestParam Long userId, @RequestParam String userName) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.saveUserNameToHash(userId, userName);
            result.put("code", 200);
            result.put("message", "用户名保存到Redis Hash成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 演示Hash结构的使用 - 获取用户名
     */
    @GetMapping("/hash/get/{userId}")
    public Map<String, Object> getUserNameFromHash(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            String userName = userService.getUserNameFromHash(userId);
            if (userName != null) {
                result.put("code", 200);
                result.put("message", "从Redis Hash获取用户名成功");
                result.put("data", userName);
            } else {
                result.put("code", 404);
                result.put("message", "Redis Hash中未找到该用户名");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        return result;
    }
}
