package com.mzl.seckilldemo1.controller;

import com.mzl.seckilldemo1.dto.UserDTO;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.result.Result;
import com.mzl.seckilldemo1.service.UserService;
import com.mzl.seckilldemo1.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.sql.Timestamp;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2021/3/5 16:00
 * @Version: 1.0
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegister")
    public String  toRegister(){
        return "register";
    }

    /**
     * 用户注册
     * @return
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public Result doRegister(UserDTO user, Model model){
        System.out.println(user);
        int count = userService.register(user);
        if (count > 0){
            return new Result(200, "注册成功", null);
        }else {
            return new Result(201, "注册失败", null);
        }

    }

}
