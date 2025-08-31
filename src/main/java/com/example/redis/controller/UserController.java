package com.example.redis.controller;

import com.example.redis.entity.User;
import com.example.redis.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 提供RESTful API接口，演示Redis的使用
 * 包含用户信息的增删改查操作以及Redis Hash结构的使用示例
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 用户服务类实例
     * 通过@Resource注解注入，由Spring容器管理
     */
    @Resource
    private UserService userService;

    /**
     * 保存用户到Redis
     * @param user 用户对象，通过请求体传入
     * @return 操作结果Map，包含状态码、消息和数据
     */
    @PostMapping("/save")
    public Map<String, Object> saveUser(@RequestBody User user) {
        // 创建返回结果Map
        Map<String, Object> result = new HashMap<>();
        try {
            // 调用UserService保存用户信息到Redis
            boolean success = userService.saveUserToRedis(user);
            if (success) {
                // 保存成功，设置成功状态码和消息
                result.put("code", 200);
                result.put("message", "用户保存到Redis成功");
                result.put("data", user);
            } else {
                // 保存失败，设置失败状态码和消息
                result.put("code", 500);
                result.put("message", "用户保存到Redis失败");
            }
        } catch (Exception e) {
            // 捕获异常，设置错误状态码和消息
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        // 返回结果
        return result;
    }

    /**
     * 从Redis获取用户
     * @param userId 用户ID，通过路径变量传入
     * @return 操作结果Map，包含状态码、消息和数据
     */
    @GetMapping("/get/{userId}")
    public Map<String, Object> getUser(@PathVariable Long userId) {
        // 创建返回结果Map
        Map<String, Object> result = new HashMap<>();
        try {
            // 调用UserService从Redis获取用户信息
            User user = userService.getUserFromRedis(userId);
            if (user != null) {
                // 获取成功，设置成功状态码、消息和数据
                result.put("code", 200);
                result.put("message", "从Redis获取用户成功");
                result.put("data", user);
            } else {
                // 未找到用户，设置404状态码和消息
                result.put("code", 404);
                result.put("message", "Redis中未找到该用户");
            }
        } catch (Exception e) {
            // 捕获异常，设置错误状态码和消息
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        // 返回结果
        return result;
    }

    /**
     * 从Redis删除用户
     * @param userId 用户ID，通过路径变量传入
     * @return 操作结果Map，包含状态码和消息
     */
    @DeleteMapping("/delete/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        // 创建返回结果Map
        Map<String, Object> result = new HashMap<>();
        try {
            // 调用UserService从Redis删除用户信息
            userService.deleteUserFromRedis(userId);
            // 删除成功，设置成功状态码和消息
            result.put("code", 200);
            result.put("message", "从Redis删除用户成功");
        } catch (Exception e) {
            // 捕获异常，设置错误状态码和消息
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        // 返回结果
        return result;
    }

    /**
     * 演示Hash结构的使用 - 保存用户名
     * @param userId 用户ID，通过请求参数传入
     * @param userName 用户名，通过请求参数传入
     * @return 操作结果Map，包含状态码和消息
     */
    @PostMapping("/hash/save")
    public Map<String, Object> saveUserNameToHash(@RequestParam Long userId, @RequestParam String userName) {
        // 创建返回结果Map
        Map<String, Object> result = new HashMap<>();
        try {
            // 调用UserService将用户名保存到Redis Hash结构
            userService.saveUserNameToHash(userId, userName);
            // 保存成功，设置成功状态码和消息
            result.put("code", 200);
            result.put("message", "用户名保存到Redis Hash成功");
        } catch (Exception e) {
            // 捕获异常，设置错误状态码和消息
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        // 返回结果
        return result;
    }

    /**
     * 演示Hash结构的使用 - 获取用户名
     * @param userId 用户ID，通过路径变量传入
     * @return 操作结果Map，包含状态码、消息和数据
     */
    @GetMapping("/hash/get/{userId}")
    public Map<String, Object> getUserNameFromHash(@PathVariable Long userId) {
        // 创建返回结果Map
        Map<String, Object> result = new HashMap<>();
        try {
            // 调用UserService从Redis Hash结构获取用户名
            String userName = userService.getUserNameFromHash(userId);
            if (userName != null) {
                // 获取成功，设置成功状态码、消息和数据
                result.put("code", 200);
                result.put("message", "从Redis Hash获取用户名成功");
                result.put("data", userName);
            } else {
                // 未找到用户名，设置404状态码和消息
                result.put("code", 404);
                result.put("message", "Redis Hash中未找到该用户名");
            }
        } catch (Exception e) {
            // 捕获异常，设置错误状态码和消息
            result.put("code", 500);
            result.put("message", "操作失败：" + e.getMessage());
        }
        // 返回结果
        return result;
    }
}
