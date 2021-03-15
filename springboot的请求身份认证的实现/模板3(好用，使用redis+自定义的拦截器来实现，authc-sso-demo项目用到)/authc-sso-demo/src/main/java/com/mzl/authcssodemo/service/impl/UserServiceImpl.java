package com.mzl.authcssodemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mzl.authcssodemo.dao.UserDao;
import com.mzl.authcssodemo.entity.User;
import com.mzl.authcssodemo.service.UserService;
import com.mzl.authcssodemo.uitl.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/1 22:40
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public JSONObject login(User user) {
        JSONObject result = new JSONObject();
        //通过用户名查询用户
        User user1 = userDao.findByUsername(user.getUsername());
        if (user1 == null || (user1 != null && !user1.getPassword().equals(user.getPassword()))){
            result.put("success", false);
            result.put("token", "");
            result.put("code", 999);
            result.put("msg", "用户名或密码不正确");
            return result;
        }
        //否则登录成功
        String token = UUID.randomUUID().toString();  //随机生成用户ticket
        token.replace("_", "");   //去掉ticket的“_”
        System.out.println("登录生成的token: " + token);
        //设置真正有效的用户token的key名称，每次请求后都要使用最新一次登录的token，防止用户使用以前的登录token也可以请求,并设置用户每次登录后的有效期
        redisTemplate.opsForValue().set("valid_token_name", token, 24, TimeUnit.HOURS);   //每次登录的有效期是24小时
        //设置用户的token身份redis（有效期为10分钟）
        redisTemplate.opsForValue().set(token, user1.getUsername(), 10L, TimeUnit.MINUTES);
        Map paramsMap = new HashMap();   //用户存储生成sign的参数（token、timestamp和username）
        paramsMap.put("username", user1.getUsername());
        paramsMap.put("token", token);
        long currentTimestamp = SignUtils.getTimestamp();
        System.out.println("登录生成的timestamp: " + currentTimestamp);
        paramsMap.put("timestamp", String.valueOf(currentTimestamp));  //long转为long类型的字符串·
        String sign = SignUtils.getSign(paramsMap, SignUtils.secretKeyOfWxh);
        System.out.println("登录生成的sign: " + sign);
        result.put("success", true);
        result.put("code", 200);
        result.put("msg", "登录成功");
        result.put("token", token);
        result.put("timestamp", currentTimestamp);
        result.put("sign", sign);
        return result;
    }

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
