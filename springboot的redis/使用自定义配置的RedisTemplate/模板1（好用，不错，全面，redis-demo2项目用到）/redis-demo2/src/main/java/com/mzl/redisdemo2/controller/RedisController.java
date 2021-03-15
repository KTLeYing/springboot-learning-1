package com.mzl.redisdemo2.controller;

import com.mzl.redisdemo2.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   RedisController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/12/16 17:05
 * @Version: 1.0
 */
@RestController
public class RedisController {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 下面主要演示测试常用&复杂点的方法，对于简单和List、set等其他方法后面需要用在再去使用了解、积累即可
     */

    //============================String=============================
    /**
     * 设置值(无期限)
     * @param
     * @param
     * @return
     */
    @RequestMapping("/set")
    public void set(){
        redisUtils.set("e", 1);
    }

    /**
     * 设置值(有期限)
     * @param
     * @param
     * @return
     */
    @RequestMapping("/set1")
    public void set1(){
        redisUtils.set("f", 2);
        redisUtils.expire("f", 10);
    }


    //============================Hash（哈希）=============================
    /**
     * 设置hash值（无期限）
     * @param
     * @return
     */
    @RequestMapping("/hashSet")
    public void hashSet(){
        redisUtils.hset("g", "3", 3);
    }

    /**
     * 设置hash值（有期限）
     * @param
     * @return
     */
    @RequestMapping("/hashSet1")
    public void hashSet1(){
        redisUtils.hset("h", "4", 4);
        redisUtils.expire("h", 30);
    }

    /**
     * 对应的key加 n
     */
    @RequestMapping("/increment")
    public void increment(){
        redisUtils.incr("e", 5);
    }

    /**
     * 对应的key的值减 n
     */
    @RequestMapping("/decrement")
    public void decrement(){
        redisUtils.decr("f", -1);
    }

    /**
     * 还有一些简单的、list、set等redis的方法到时候一一去掌握，积累
     */


}
