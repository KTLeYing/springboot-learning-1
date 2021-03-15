package com.mzl.SSOdemo2.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.SSOdemo2.dao.UserDao;
import com.mzl.SSOdemo2.entity.User;
import com.mzl.SSOdemo2.result.ResultMap;
import com.mzl.SSOdemo2.service.UserService;
import com.mzl.SSOdemo2.util.CookieUtils;
import com.mzl.SSOdemo2.util.MD5Util;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/2/8 0:28
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public ResultMap register(User user){
        //通过用户名查询用户
        User user1 = userDao.findByUsername(user.getUsername());
        if (user1 == null){
            //密码加密
            String password = MD5Util.getMD5(user.getPassword());
            System.out.println(password);
            user.setPassword(password);
            //添加用户
            int num = userDao.addUser(user);
            if (num > 0){
                return new ResultMap(200, "用户注册成功", null);
            }else {
                return new ResultMap(500, "数据库错误,用户注册失败", null);
            }
        }
        else {
            return new ResultMap(201, "用户名已存在", null);
        }
    }

    /**
     * 用户登出
     * @param token
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultMap logout(String token, HttpServletRequest request, HttpServletResponse response) {
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.del(token);  //删除用户redis
            CookieUtils.deleteCookie(request, response, "TOKEN");  //删除用户的cookie
            return new ResultMap(200, "登出成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMap(201, "登出失败", null);
        }
    }

    /**
     * 获取用户的token的值（用户的信息）
     * @param token
     * @return
     */
    @Override
    public ResultMap getToken(String token) {
        Jedis jedis = jedisPool.getResource();
        String userStr = jedis.get(token);
        if (!StringUtils.isEmpty(userStr)){
            User user = JSON.parseObject(userStr, User.class);
            user.setPassword("");  //密码设置为空是为了用户的密码安全，在浏览器客户端不显示出来
            return new ResultMap(200, "获取token成功", user);
        }else {
            return new ResultMap(201, "获取token失败", null);
        }
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public ResultMap login(User user, HttpServletRequest request, HttpServletResponse response) {
        //通过用户名查询用户
        User user1 = userDao.findByUsername(user.getUsername());
        if (user1 != null){
            //加密用户登录的密码
            String password = MD5Util.getMD5(user.getPassword());
            if (password.equals(user1.getPassword())){
                //创建一个唯一的key(存入redis)
                String key = UUID.randomUUID().toString();
                System.out.println(key);
                String value = JSON.toJSON(user1).toString();   //把普通的对象object转换为json格式的对象，再将json对象转为json字符串
                System.out.println(value);
                //获取jedis对象
                Jedis jedis = jedisPool.getResource();
                jedis.set(key, value);  //设置redis
                jedis.expire(key, 60 * 60 * 24 * 7);  //设置redis期限
                //设置用户的cookie
                CookieUtils.setCookie1(request, response, "TOKEN", key, 60 * 60 * 24 * 7);
                return new ResultMap(200, "登录成功", null);
            }else {
                return new ResultMap(202, "用户名或密码错误", null);
            }
        }else {
            return new ResultMap(201, "此用户不存在", null);
        }
    }
}
