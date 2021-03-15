package com.example.demo;

import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    @RequestMapping(value = "/alluser.do",method = RequestMethod.GET)
    public String getallusers(Model model) {
        stringRedisTemplate.opsForValue().set("keytest", "cuiyw");
        final String keytest = stringRedisTemplate.opsForValue().get("keytest");
        System.out.println("1111:"+keytest);
        String key = "1857XXXX040";
        redisCacheTemplate.opsForValue().set(key, new User(key, "cuiyw", 18));
        // TODO 对应 String（字符串）
        final User user = (User) redisCacheTemplate.opsForValue().get(key);
        System.out.println("user111:"+user);
        return "userlist";
    }

}
