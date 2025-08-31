package com.example.redis.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * 封装Redis的常用操作，提供简便的Redis访问接口
 */
@Component
public class RedisUtil {

    /**
     * RedisTemplate是Spring Data Redis提供的用于执行Redis操作的核心类
     * 通过@Resource注解注入，由Spring容器管理其生命周期
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return 是否成功
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                // 设置key的过期时间
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            // 打印异常堆栈信息，实际项目中建议使用日志框架记录
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        // 获取key的剩余过期时间，单位为秒
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            // 检查key是否存在
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            // 打印异常堆栈信息，实际项目中建议使用日志框架记录
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                // 删除单个key
                redisTemplate.delete(key[0]);
            } else {
                // 删除多个key
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        // 如果key为null则返回null，否则从Redis中获取值
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            // 将值存入Redis，使用默认的过期时间
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            // 打印异常堆栈信息，实际项目中建议使用日志框架记录
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                // 将值存入Redis，并设置过期时间
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                // 如果时间小于等于0，则使用无过期时间的方式存储
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            // 打印异常堆栈信息，实际项目中建议使用日志框架记录
            e.printStackTrace();
            return false;
        }
    }

    // ============================Hash=============================

    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        // 从Redis Hash中获取指定key和field的值
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        // 获取Redis Hash中指定key的所有field和value
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            // 将Map中的所有field和value存入Redis Hash
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            // 打印异常堆栈信息，实际项目中建议使用日志框架记录
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            // 向Redis Hash中存入指定key、field和value的数据
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            // 打印异常堆栈信息，实际项目中建议使用日志框架记录
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        // 从Redis Hash中删除指定key和fields的数据
        redisTemplate.opsForHash().delete(key, item);
    }

    // 更多Redis操作方法可以根据需要添加...
}
