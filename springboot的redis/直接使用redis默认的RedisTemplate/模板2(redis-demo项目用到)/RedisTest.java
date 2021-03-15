package com.mzl.redisdemo;

import com.mzl.redisdemo.config.RedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName :   RedisTest
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/8/20 22:01
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisConfig redisConfig;

    /**
     * 设置key
     */
    @Test
    public void set(){
        redisConfig.set("key1", "value1");
    }

    /**
     * 获取key
     */
    @Test
    public void get(){
        System.out.println(redisConfig.get("key1"));
    }


}
