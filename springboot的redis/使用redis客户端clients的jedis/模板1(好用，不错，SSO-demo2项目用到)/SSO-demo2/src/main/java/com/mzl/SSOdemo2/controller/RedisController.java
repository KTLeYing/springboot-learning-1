package com.mzl.SSOdemo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName :   RedisController
 * @Description:
 * @Author: mzl
 * @CreateDate: 2021/2/8 15:07
 * @Version: 1.0
 */
@RestController
public class RedisController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 设置redis的测试1
     * @return
     */
    @RequestMapping("/setRedis1")
    public String setRedis1(){
        String key = "test1";
        String value = "value1";
        //获取jedis对象
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);  //设置redis
        return "设置redis成功";
    }

    /**
     * 设置redis的测试2（有期限）
     * @return
     */
    @RequestMapping("/setRedis2")
    public String setRedis2(){
        String key = "test2";
        String value = "value2";
        //获取jedis对象
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);  //设置redis
        jedis.expire(key, 60 * 60 * 24 * 7);  //设置redis期限
        return "设置redis成功";
    }

    /**
     * 获取redis的测试
     * @return
     */
    @RequestMapping("/getRedis")
    public String getRedis(){
        String key = "test2";
        //获取jedis对象
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);  //获取redis
        return "设置redis成功:" + key + "的值为 " + value;
    }

    /**
     * 判断是否存在redis的测试
     * @return
     */
    @RequestMapping("/existRedis")
    public String existRedis(){
        String key = "test2";
        //获取jedis对象
        Jedis jedis = jedisPool.getResource();
        boolean exist = jedis.exists(key);  //删除redis
        if (exist){
            return "redis存在";
        }else {
            return "redis不存在";
        }
    }

    /**
     * 获取redis的剩余期限ttl的测试
     * @return
     */
    @RequestMapping("/ttlRedis")
    public String ttlRedis(){
        String key = "test2";
        //获取jedis对象
        Jedis jedis = jedisPool.getResource();
        long time = jedis.ttl(key);  //删除redis
        return "redis剩余时间为：" + time;
    }

    /**
     * 删除redis的测试
     * @return
     */
    @RequestMapping("/delRedis")
    public String delRedis(){
        String key = "test2";
        //获取jedis对象
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);  //删除redis
        return "删除redis成功";
    }

}
