package com.mzl.redisdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName :   RedisTest1
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/8/20 23:14
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest1 {

    /**
     * 使用的String序列化
     */
    @Autowired
    private StringRedisTemplate redisStringTemplate;

    /**
     * 使用jdk默认的序列化
     */
    @Autowired
    private RedisTemplate redisTemplate;

  /**
   * 补充：StringRedisTemplate与RedisTemplate的区别
   * <p>1. 两者的关系是StringRedisTemplate继承RedisTemplate。
   * <p>2.
   * 两者的数据是不共通的；也就是说StringRedisTemplate只能管理StringRedisTemplate里面的数据，RedisTemplate只能管理RedisTemplate中的数据。
   * <p>3. StringRedisTemplate默认采用的序列化策略有两种，一种是String的序列化策略，一种是JDK的序列化策略。
   * <p>StringRedisTemplate默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的。
   */
  @Test
  public void testStringRedisTemplate() {
        redisStringTemplate.opsForValue().set("stringRedisTemplate", "ok");
        System.out.println(redisStringTemplate.opsForValue().get("stringRedisTemplate"));
    }


    /**
     * RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。
     */
    @Test
    public void testRedisTemplate() {
        redisTemplate.opsForValue().set("redisTemplate", "hhh");
        System.out.println(redisTemplate.opsForValue().get("redisTemplate"));
    }

}
