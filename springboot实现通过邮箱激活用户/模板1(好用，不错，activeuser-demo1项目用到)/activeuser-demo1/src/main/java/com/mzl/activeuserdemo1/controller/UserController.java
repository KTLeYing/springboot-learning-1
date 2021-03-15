package com.mzl.activeuserdemo1.controller;

import com.mzl.activeuserdemo1.pojo.Mail;
import com.mzl.activeuserdemo1.pojo.User;
import com.mzl.activeuserdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2020/11/27 21:17
 * @Version: 1.0
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @return
     */
    @RequestMapping("/register")
    public String register(User user){
        user.setStatus(0);
//        String code = UUID.randomUUID().toString().replace("-", "");
        String code = UUID.randomUUID().toString();
        user.setCode(code);
        Boolean flag = userService.register(user);
        if (flag){
            return "success";    //注册成功页面
        }else {
            return "fail";
        }
    }

    /**
     * 校验邮箱中的code激活账户
     * 首先根据激活码code查询用户，之后再把状态修改为"1"
     * @param code
     * @return
     */
    @RequestMapping("/checkCode")
    public String checkCode(String code) {
        User user = userService.checkCode(code);
        if (user != null){
            user.setStatus(1);
            //把code验证码清空，已经不需要了
            user.setCode("");
            //更新用户激活状态
            userService.updateUserStatus(user);
        }

        return "activeSuccess";   //激活成功，然后跳转去登录
    }

    /**
     * 跳转到登录页面
     * @param user
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(User user){
        return "login";
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public String login(User user){
        User user1 = userService.login(user);
        if (user1 != null){
            return "welcome";
        }
        return "login";
    }

}
