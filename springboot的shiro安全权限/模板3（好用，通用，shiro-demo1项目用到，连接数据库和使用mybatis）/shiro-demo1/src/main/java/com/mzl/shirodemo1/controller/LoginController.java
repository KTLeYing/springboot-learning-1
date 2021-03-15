package com.mzl.shirodemo1.controller;

import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.util.parsing.json.JSONObject;

/**
 * @ClassName :   LoginController
 * @Description: 登录控制器
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:14
 * @Version: 1.0
 */
@RestController
@Slf4j
public class LoginController {

    @RequestMapping("/login")
    public String login(String username, String password, @RequestParam(value = "rememberMe", required = true, defaultValue = "false") boolean rememberMe){
        log.info(username + " " + password + " " + rememberMe);
        Subject subject = SecurityUtils.getSubject();  //创建安全管理的主体，用于·获取用户的登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);

        try{
            subject.login(token);  //获取用户的信息到realm中去验证
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"Msg\": \"您的账号或密码输入错误！\", \"state\": \"failed\"}";

        }
        return "{\"Msg\": \"登录成功\", \"state\": \"success\"}";
    }

    @RequestMapping("/")
    public String index(){
        return "no permission";
    }

}
