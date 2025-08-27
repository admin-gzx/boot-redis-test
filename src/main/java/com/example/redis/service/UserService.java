package com.example.redis.service;

import com.example.redis.entity.User;
import com.example.redis.util.RedisUtil;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 用户服务类
 * 演示如何在服务层使用Redis
 */
@Service
public class UserService {

    // Redis工具类
    @Resource
    private RedisUtil redisUtil;
    
    // Redis中存储用户信息的key前缀
    private static final String USER_KEY_PREFIX = "user:";

    /**
     * 保存用户信息到Redis
     * @param user 用户对象
     * @return 是否保存成功
     */
    public boolean saveUserToRedis(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        // 构建Redis的key：user:1
        String key = USER_KEY_PREFIX + user.getId();
        // 设置过期时间为1小时（3600秒）
        return redisUtil.set(key, user, 3600);
    }

    /**
     * 从Redis中获取用户信息
     * @param userId 用户ID
     * @return 用户对象
     */
    public User getUserFromRedis(Long userId) {
        if (userId == null) {
            return null;
        }
        // 构建Redis的key：user:1
        String key = USER_KEY_PREFIX + userId;
        // 从Redis中获取用户信息
        Object obj = redisUtil.get(key);
        if (obj != null && obj instanceof User) {
            return (User) obj;
        }
        return null;
    }

    /**
     * 从Redis中删除用户信息
     * @param userId 用户ID
     */
    public void deleteUserFromRedis(Long userId) {
        if (userId != null) {
            // 构建Redis的key：user:1
            String key = USER_KEY_PREFIX + userId;
            // 从Redis中删除用户信息
            redisUtil.del(key);
        }
    }

    /**
     * 演示Hash结构的使用
     * @param userId 用户ID
     * @param userName 用户名
     */
    public void saveUserNameToHash(Long userId, String userName) {
        if (userId == null || userName == null) {
            return;
        }
        // 构建Redis的hash key：user:hash
        String hashKey = "user:hash";
        // 向hash中存入数据，field为用户ID，value为用户名
        redisUtil.hset(hashKey, userId.toString(), userName);
    }

    /**
     * 从Hash中获取用户名
     * @param userId 用户ID
     * @return 用户名
     */
    public String getUserNameFromHash(Long userId) {
        if (userId == null) {
            return null;
        }
        // 构建Redis的hash key：user:hash
        String hashKey = "user:hash";
        // 从hash中获取数据
        Object obj = redisUtil.hget(hashKey, userId.toString());
        return obj != null ? obj.toString() : null;
    }
}
