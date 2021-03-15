package com.mzl.securitydemo5.controller;

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
//    @ResponseBody
//    @GetMapping("admin")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String printAdmin(){
//        return "你有ROLE_ADMIN角色";
//    }

//    @ResponseBody
//    @RequestMapping("user")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public String printUser(){
//        return "你有ROLE_USER角色";
//    }

    /**
     * 某一角色某一路径的权限
     * PreAuthorize("hasPermission('/admin','r')")是关键，参数1指明了访问该接口需要的url(与接口真正访问路径无关，只是hasPermission（）里面的自定义
     * 路径)，参数2指明了访问该接口需要的权限。
     * @return
     */
    @RequestMapping("admin")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin', 'r')")
    public String printAdminR(){
        return "如果你看见这句话，说明你访问/admin路径具有r权限";
    }

    /**
     * 某一角色某一路径的权限
     * PreAuthorize("hasPermission('/admin','r')")是关键，参数1指明了访问该接口需要的ur(与接口真正访问路径无关，只是hasPermission（）里面的自定义
     * 路径)里面的自定义
     * 路径)，参数2指明了访问该接口需要的权限。
     * @return
     */
    @RequestMapping("user")
    @ResponseBody
    @PreAuthorize("hasPermission('/user', 'c')")
    public String printAdminC(){
        return "如果你看见这句话，说明你访问/user路径具有c权限";
    }



}
