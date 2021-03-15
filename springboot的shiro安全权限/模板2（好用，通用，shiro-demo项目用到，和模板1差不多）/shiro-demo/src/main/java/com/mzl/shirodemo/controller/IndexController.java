package com.mzl.shirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName : IndexController @Description: shiro测试的首页控制器 @Author: mzl @CreateDate: 2020/9/16
 * 19:46 @Version: 1.0
 */
/**
 * 其实Shiro框架并不难，我梳理了一下，你只需要学会以下内容基本就足够了：
 * <1>登陆、授权流程
 * <2>shiro过滤器链
 * <3>整合Springboot、redis做共享会话
 * <4>结合xxl-sso实现单点登录
 */
@Controller
public class IndexController {

    @Autowired
    HttpServletRequest request;

    /**
     * 起始页面
     * @return
     */
    @RequestMapping({"/", "/index"})  //可以使用多种路径访问，用逗号隔开
    public String index(){
        System.out.println("已登录，正在访问！");
        return "/index";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    /**
     * 登录操作
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(String username, String password) {
        System.out.println(username);
        System.out.println(password);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password); //获取用户信息，存入token
        try {
            //Subject是大佬，主题，连接到Security Manager
            // (1)认证：Subject进行login操作，参数是封装了用户信息的token，Security Manager委托给Authenticator进行认证逻辑处理
            // (2)授权：调用Subject.isPermitted/hasRole接口，委托给SecurityManager，而SecurityManager接着会委托给Authorizer
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                request.setAttribute("errorMess", "用户不存在");
            } else if (e instanceof LockedAccountException) {
                request.setAttribute("errorMess", "用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                request.setAttribute("errorMess", "密码错误");
            } else {
                request.setAttribute("errorMess", "用户认证失败");
            }
            return "/login";
        }

//        return "redirect:/";
        //或
        return "redirect:/index";
    }

    /**
     * 用户退出登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout(); //安全管理器自动清除用户信息
        return "redirect:/login";
    }

}
