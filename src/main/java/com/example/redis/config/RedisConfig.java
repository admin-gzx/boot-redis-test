package com.example.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 用于配置RedisTemplate，自定义序列化方式
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate
     * 自定义序列化方式，解决默认序列化方式导致的key乱码问题
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(factory);
        
        // 创建字符串序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 创建JSON序列化器
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        
        // 设置key的序列化方式为字符串
        template.setKeySerializer(stringRedisSerializer);
        // 设置hash的key的序列化方式为字符串
        template.setHashKeySerializer(stringRedisSerializer);
        
        // 设置value的序列化方式为JSON
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置hash的value的序列化方式为JSON
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        
        // 初始化RedisTemplate
        template.afterPropertiesSet();
        
        return template;
    }
}
