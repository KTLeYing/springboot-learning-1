package com.mzl.securitydemo3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 登录失败跳转的页面，用HttpServletResponse的write()方法来产生的一个页面，session 中的 SPRING_SECURITY_LAST_EXCEPTION是出现异常时security自动帮你生成的一个异常的会话
     * @param request
     * @param response
     */
    @RequestMapping("loginError")
    public void loginError(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");
        AuthenticationException exception = (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try{
            response.getWriter().write(exception.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录错误跳转的页面
     * @return
     */
    @RequestMapping("loginError1")
    public String loginError1(){
        return "loginError";
    }

}
