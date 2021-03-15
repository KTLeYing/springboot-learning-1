package com.mzl.securitydemo7.controller;

import com.mzl.securitydemo7.result.Result;
import com.mzl.securitydemo7.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   LoginController
 * @Description: 登录控制器
 * @Author: mzl
 * @CreateDate: 2020/11/12 20:51
 * @Version: 1.0
 */
@Controller
@Slf4j
@CrossOrigin  //可以跨域请求
public class LoginController {

    @Autowired
    private SmsService smsService;

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

//    @RequestMapping("/sms/code")
//    @ResponseBody
//    public void sms(String mobile, HttpSession session) {
//        System.out.println("yyy");
//        int code = (int) Math.ceil(Math.random() * 9000 + 1000);
//
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("mobile", mobile);
//        map.put("code", code);
//
//        session.setAttribute("smsCode", map);
//
//        System.out.println(session.getId() + " " + mobile + " " + code);
//
//        log.info("{}：为 {} 设置短信验证码：{}", session.getId(), mobile, code);
//    }

    /**
     * 发送短信
     * @param
     * @return
     */
    @RequestMapping("/sms/code")
    @ResponseBody
    public Result sendSms1(String mobile, HttpSession session){
        System.out.println("yyy");
        Result result = smsService.sendSms(mobile);

        Map<String, Object> map = new HashMap<>(16);
        map.put("mobile", mobile);
        map.put("code", result.getSmsCode());

        session.setAttribute("smsCode", map);

        System.out.println(session.getId() + " " + mobile + " " + result.getSmsCode());

        log.info("{}：为 {} 设置短信验证码：{}", session.getId(), mobile, result.getSmsCode());

        return result;
    }

}
