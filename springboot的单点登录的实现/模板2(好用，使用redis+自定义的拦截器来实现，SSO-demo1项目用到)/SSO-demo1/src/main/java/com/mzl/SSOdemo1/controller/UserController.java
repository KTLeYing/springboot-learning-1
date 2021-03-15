package com.mzl.SSOdemo1.controller;

import com.mzl.SSOdemo1.entity.User;
import com.mzl.SSOdemo1.interceptor.InterceptFlag;
import com.mzl.SSOdemo1.result.ResultMap;
import com.mzl.SSOdemo1.service.AuthTokenService;
import com.mzl.SSOdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2021/1/30 9:52
 * @Version: 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private AuthTokenService authTokenService;
    @Autowired
    private UserService userService;

    /**
     * 获取用户的token
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getToken")
    public ResultMap getToken(String userId) throws Exception {
        return new  ResultMap(200, "获取用户token成功", authTokenService.getToken(userId));
    }

    /**
     * 获取所有的用户
     * @return
     */
    @RequestMapping("/findAllUser")
    public ResultMap findAllUser(){
        return new ResultMap(200, "获取所有用户成功", true);
    }

    /**
     * 模拟登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public ResultMap login(User user) throws Exception {
        return userService.login(user);
    }

    /**
     * 模拟单点登录
     * @return
     * @throws Exception
     */
    @RequestMapping("/testSSO")
    @InterceptFlag   //请求接口有拦截标准的注解才会被拦截器拦截处理
    public ResultMap testSSO() throws Exception {
        return userService.testSSO();
    }

}
