package com.mzl.ratelimiterdemo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @ClassName :   RedisConfig
 * @Description: redis配置类
 * @Author: mzl
 * @CreateDate: 2020/11/9 1:39
 * @Version: 1.0
 */
@Configuration
public class RedisConfig {

    @Bean
    public  RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory connection){
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(connection);
        return template;
    }

}
