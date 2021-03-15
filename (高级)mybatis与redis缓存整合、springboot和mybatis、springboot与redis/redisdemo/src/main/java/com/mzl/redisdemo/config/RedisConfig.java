package com.mzl.redisdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName :   RedisConfig
 * @Description: redis配置类(使用redisTemplate)
 * @Author: mzl
 * @CreateDate: 2020/9/13 16:25
 * @Version: 1.0
 */
@Configuration  //告诉spring容器是各配置类对象来的
public class RedisConfig {

    @Bean  //启动时注入返回的redisTemplate对象到spring容器中作为原料，spring容器就会给这个方法生成一个对象
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate<>();
        //创建redis序列化对象
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //使用redis连接工厂连接RedisTemplate
        redisTemplate.setConnectionFactory(factory);

        //key序列化，并加入到redisTemplate对象中
        redisTemplate.setKeySerializer(redisSerializer);
        //value序列化
        redisTemplate.setValueSerializer(redisSerializer);
        //value和hash map序列化
        redisTemplate.setHashValueSerializer(redisSerializer);
        //key和hash map序列化
        redisTemplate.setHashKeySerializer(redisSerializer);

        //返回各redis属性序列化完redisTemplate对象
        return redisTemplate;
    }
}
