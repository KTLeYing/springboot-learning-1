package com.mzl.redisdemo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   RedisController
 * @Description: redis控制器
 * @Author: mzl
 * @CreateDate: 2020/12/16 13:51
 * @Version: 1.0
 */
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    //============================String=============================
    /**
     * 设置值(无期限)
     * @param
     * @param
     * @return
     */
    @RequestMapping("/set")
    public void set(){
         redisTemplate.opsForValue().set("1", 1);
    }

    /**
     * 设置值(有期限)
     * @param
     * @param
     * @return
     */
    @RequestMapping("/set1")
    public void set1(){
        redisTemplate.opsForValue().set("2", 2, 3000);
        redisTemplate.expire("2", 10, TimeUnit.SECONDS);
    }


    //============================Hash（哈希）=============================
    /**
     * 设置hash值（无期限）
     * @param
     * @return
     */
    @RequestMapping("/hashSet")
    public void hashSet(){
        redisTemplate.opsForHash().put("3", "3.1", 3);
    }

    /**
     * 设置hash值（有期限）
     * @param
     * @return
     */
    @RequestMapping("/hashSet1")
    public void hashSet1(){
       redisTemplate.opsForHash().put("4", "4.1", 4);
       redisTemplate.expire("4", 30, TimeUnit.SECONDS);
    }

    /**
     * 对应的key加 n
     */
    @RequestMapping("/increment")
    public void increment(){
        redisTemplate.opsForValue().increment("1", 5);
    }

    /**
     * 对应的key的值减 n
     */
    @RequestMapping("/decrement")
    public void decrement(){
        redisTemplate.opsForValue().decrement("2", -1);
    }

    /**
     * 还有list、set等redis的方法到时候一一去掌握，积累
     */

}
