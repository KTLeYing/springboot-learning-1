package com.mzl.securitydemo2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :   LoginController
 * @Description: 登录控制器
 * @Author: mzl
 * @CreateDate: 2020/11/12 20:51
 * @Version: 1.0
 */
@Controller
@Slf4j
public class LoginController {

    /**
     * 跳转到主页
     * @return
     */
    @RequestMapping("home")
    public String shoeHome(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户：" + name);
        return "home";
    }

    /**
     * 用户登录接口
     * @return
     */
    @RequestMapping("login")
    public String showLogin() {
        return "login";
    }

    /**
     * 管理员
     * @return
     */
    @ResponseBody
    @GetMapping("admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin(){
        return "你有ROLE_ADMIN角色";
    }

    @ResponseBody
    @RequestMapping("user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser(){
        return "你有ROLE_USER角色";
    }

}
