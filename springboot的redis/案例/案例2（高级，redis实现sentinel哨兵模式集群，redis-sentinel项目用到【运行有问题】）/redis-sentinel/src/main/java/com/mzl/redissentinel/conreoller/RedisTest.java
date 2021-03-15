package com.mzl.redissentinel.conreoller;

import com.mzl.redissentinel.entity.User;
import com.mzl.redissentinel.util.RedisUtil;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   RedisTest
 * @Description: redis测试类
 * @Author: mzl
 * @CreateDate: 2021/1/25 0:06
 * @Version: 1.0
 */
@RestController
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * redis哨兵集群测试
     */
    @RequestMapping("/test")
    public void findInterfaceTest(){
        redisUtil.set("String1", "str");
        System.out.println("String1:  " + redisUtil.get("String1"));

        redisUtil.set("String2", "str2", 60 * 2);
        System.out.println("String2:  " + redisUtil.get("String2"));

        Map<String, Object> map = new HashMap<>();
        map.put("int1", 1);
        map.put("int2", 2);
        map.put("int3", 3);
        redisUtil.set("map1", map, 60 * 2);
        System.out.println("map1:  " + redisUtil.get("map1"));

        User user = new User(1, "mzl");
        redisUtil.set("user", user, 60 * 2);
        System.out.println("user: " + redisUtil.get("user"));

        User user1 = new User(2, "lisi");
        redisUtil.set("user1", user, 60 * 2);
        System.out.println("user1: " + redisUtil.get("user1"));

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        redisUtil.set("userList:  " + userList, 60 * 2);
        System.out.println("userList:  " + redisUtil.get("userList"));
    }

}
