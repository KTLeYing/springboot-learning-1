package com.mzl.SSOdemo2.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.mzl.SSOdemo2.entity.User;
import com.mzl.SSOdemo2.result.ResultMap;
import com.mzl.SSOdemo2.service.UserService;
import com.mzl.SSOdemo2.util.CookieUtils;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2021/2/8 0:27
 * @Version: 1.0
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 跳转到首页面
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        //获取用户客户端cookie
        String cookieName = "TOKEN";
        String cookies = CookieUtils.getCookieValue(request, cookieName);
        if (!StringUtils.isEmpty(cookies)){
            System.out.println("cookies还存在，不用重新登录，单点登录成功！cookies的值:" + cookies);
            Jedis jedis = jedisPool.getResource();
            String userStr = jedis.get(cookies);
            System.out.println(userStr);
            User user = JSON.parseObject(userStr, User.class);
            request.setAttribute("user", user);
            return "index";
        }
        return "login";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request){
        //获取用户客户端cookie
        String cookieName = "TOKEN";
        String cookies = CookieUtils.getCookieValue(request, cookieName);
        if (!StringUtils.isEmpty(cookies)){
            System.out.println("cookies还存在，不用重新登录，单点登录成功！cookies的值:" + cookies);
            Jedis jedis = jedisPool.getResource();
            String userStr = jedis.get(cookies);
            System.out.println(userStr);
            User user = JSON.parseObject(userStr, User.class);
            request.setAttribute("user", user);
            return "index";
        }
        return "login";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResultMap register(User user){
        return userService.register(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResultMap login(User user, HttpServletRequest request, HttpServletResponse response){
        return userService.login(user, request, response);
    }

    /**
     * 用户登出
     * @param token
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ResultMap logout(String token, HttpServletRequest request, HttpServletResponse response){
        return userService.logout(token, request, response);
    }

    /**
     * 获取用户的token的值（用户的信息）
     * @param token
     * @return
     */
    @RequestMapping("/getToken")
    @ResponseBody
    public ResultMap getToken(String token){
        return userService.getToken(token);
    }

    /**
     * 获取用户的Cookie的值
     * @param
     * @return
     */
    @RequestMapping("/getCookie")
    @ResponseBody
    public String getCookie(HttpServletRequest request){
        String cookie = CookieUtils.getCookieValue(request, "TOKEN");
        return cookie;
    }

}
