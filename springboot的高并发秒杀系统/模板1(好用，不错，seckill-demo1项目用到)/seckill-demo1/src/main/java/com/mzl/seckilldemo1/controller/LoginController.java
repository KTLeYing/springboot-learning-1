package com.mzl.seckilldemo1.controller;

import com.mzl.seckilldemo1.dto.LoginDTO;
import com.mzl.seckilldemo1.result.Result;
import com.mzl.seckilldemo1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @ClassName :   LoginController
 * @Description: 登录控制器
 * @Author: mzl
 * @CreateDate: 2021/3/1 17:47
 * @Version: 1.0
 */
@RequestMapping("/login")
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 处理用户登录
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, @Valid LoginDTO loginDTO){
        System.out.println("uuu");
        String token = userService.login(response, loginDTO);
        System.out.println("yyy");
        return new Result(200, "登录成功", token);
    }




}
