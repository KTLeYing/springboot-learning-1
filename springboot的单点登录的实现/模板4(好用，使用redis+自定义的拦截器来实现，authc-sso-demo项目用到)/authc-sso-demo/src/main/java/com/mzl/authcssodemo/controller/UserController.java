package com.mzl.authcssodemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mzl.authcssodemo.entity.User;
import com.mzl.authcssodemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @RequestMapping("/findUserByUsername")
    public User findUserByUsername(String username){
        return userService.findUserByUsername(username);
    }

    /**
     * 通用的测试接口
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
     * 模拟单点登录的测试(直接利用拦截器的认证处理过程实现，和用户的请求身份认证是类似的，直接使用)
     * @return
     */
    @RequestMapping("/testSSO")
    public String testSSO(){
        return "单点登录成功，无需再次登录";
    }

}
