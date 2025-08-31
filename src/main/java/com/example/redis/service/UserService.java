package com.example.redis.service;

import com.example.redis.entity.User;
import com.example.redis.util.RedisUtil;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 用户服务类
 * 演示如何在服务层使用Redis进行数据操作
 * 提供用户信息的增删改查功能
 */
@Service
public class UserService {

    /**
     * Redis工具类实例
     * 通过@Resource注解注入，由Spring容器管理
     */
    @Resource
    private RedisUtil redisUtil;
    
    /**
     * Redis中存储用户信息的key前缀
     * 用于构建用户信息在Redis中的存储key
     */
    private static final String USER_KEY_PREFIX = "user:";

    /**
     * 保存用户信息到Redis
     * @param user 用户对象
     * @return 是否保存成功
     */
    public boolean saveUserToRedis(User user) {
        // 检查用户对象及其ID是否为空
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
        // 检查用户ID是否为空
        if (userId == null) {
            return null;
        }
        // 构建Redis的key：user:1
        String key = USER_KEY_PREFIX + userId;
        // 从Redis中获取用户信息
        Object obj = redisUtil.get(key);
        // 检查获取到的对象是否为User类型，如果是则返回，否则返回null
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
        // 检查用户ID是否为空
        if (userId != null) {
            // 构建Redis的key：user:1
            String key = USER_KEY_PREFIX + userId;
            // 从Redis中删除用户信息
            redisUtil.del(key);
        }
    }

    /**
     * 演示Hash结构的使用
     * 将用户名存储到Redis Hash结构中
     * @param userId 用户ID
     * @param userName 用户名
     */
    public void saveUserNameToHash(Long userId, String userName) {
        // 检查用户ID和用户名是否为空
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
        // 检查用户ID是否为空
        if (userId == null) {
            return null;
        }
        // 构建Redis的hash key：user:hash
        String hashKey = "user:hash";
        // 从hash中获取数据
        Object obj = redisUtil.hget(hashKey, userId.toString());
        // 如果获取到的数据不为空，则返回其字符串表示，否则返回null
        return obj != null ? obj.toString() : null;
    }
}
