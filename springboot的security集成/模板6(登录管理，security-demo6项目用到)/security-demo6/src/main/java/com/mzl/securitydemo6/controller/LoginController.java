package com.mzl.securitydemo6.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

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

    @Autowired
    private SessionRegistry sessionRegistry;

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

    @RequestMapping("login/invalid")
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String invalid(){
        return "Session已过期，请重新登录!";
    }

    /**
     * 踢出用户
     * sessionRegistry.getAllPrincipals(); 获取所有 principal 信息
     * 通过 principal.getUsername 是否等于输入值，获取到指定用户的 principal
     * sessionRegistry.getAllSessions(principal, false)获取该 principal 上的所有 session
     * 通过 sessionInformation.expireNow() 使得 session 过期
     * @param username
     * @return
     */
    @RequestMapping("kick")
    @ResponseBody
    public String removeSessionByUsername(String username){
        int count = 0;

        //获取所有的session用户
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object principal : users) {
            if (principal instanceof User){  //principal是否为user的实例
                String principalName = ((User) principal).getUsername();
                if (principalName.equals(username)){   //是否在security的principals里面存在该用户
                    // 参数二：是否包含过期的Session，（因为一个用户可能有多个session）
                    List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
                    if (sessionInformations != null && sessionInformations.size() > 0){  //该用户存在session
                        for (SessionInformation sessionInformation : sessionInformations) {
                            sessionInformation.expireNow();  //设置该username的session过期，即把用户踢出系统
                            count++;   //被踢出的session数+1（因为一个用户可能有多个session）
                        }
                    }
                }
            }
        }

        return "操作成功，一共清除的" + count + "个session";
    }

}
