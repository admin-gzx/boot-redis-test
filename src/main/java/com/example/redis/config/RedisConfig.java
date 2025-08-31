package com.example.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 配置RedisTemplate以解决键值序列化问题，确保Redis中存储的数据可读性
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate
     * 通过自定义序列化器解决Redis中数据乱码问题
     * @param connectionFactory Redis连接工厂，由Spring Boot自动配置提供
     * @return 配置好的RedisTemplate实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 创建RedisTemplate实例，用于执行Redis操作
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(connectionFactory);
        
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        // 这样可以确保key是可读的字符串而不是乱码
        template.setKeySerializer(new StringRedisSerializer());
        // 使用GenericJackson2JsonRedisSerializer来序列化和反序列化redis的value值
        // 这样可以确保value是可读的JSON格式而不是乱码
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        
        // 设置hash key和value的序列化器
        // 用于处理Redis Hash结构的数据
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        
        // 初始化template
        template.afterPropertiesSet();
        return template;
    }
}
