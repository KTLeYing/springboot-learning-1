package com.mzl.apidesigndemo2.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.mzl.apidesigndemo2.entity.User;
import com.mzl.apidesigndemo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   TokenSignController
 * @Description: token签名的控制器(用户控制器)
 * @Author: mzl
 * @CreateDate: 2021/2/1 22:38
 * @Version: 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public JSONObject login(User user){
        return userService.login(user);
    }

    /**
     * 通过用户名查询用户(后端的要先登录获取token、sign、timestamp，然后用这些参数来请求其他的测试接口来进行测试，前后端分离使用，类似师兄那种)
     * @param username
     * @return
     */
    @RequestMapping("/findUserByUsername")
    public User findUserByUsername(String username){
        return userService.findUserByUsername(username);
    }

    /**
     * 通用的测试接口(后端的要先登录获取token、sign、timestamp，然后用这些参数来请求其他的测试接口来进行测试，前后端分离使用，类似师兄那种)
     * @return
     */
    @RequestMapping("/commonTest")
    public Map commonTest(){
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println("执行了通用的测试接口...");
        resultMap.put("success:", true);
        resultMap.put("code", 200);
        resultMap.put("msg", "恭喜你可以访问");
        return resultMap;
    }

    /**
     * 请求拦截信息返回跳转接口
     * @return
     */
    @RequestMapping("/error/{msg}")
    public String error(@PathVariable String msg){
        return msg;
    }


    /**
     * 获取当前时间戳
     * @return
     */
    @RequestMapping("/getTimestamp")
    public long getTimestamp(){
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取的签名
     * @return
     */
    @RequestMapping("/getSign")
    public String getSign(HttpServletRequest request){
        return userService.getSign(request);
    }
}
