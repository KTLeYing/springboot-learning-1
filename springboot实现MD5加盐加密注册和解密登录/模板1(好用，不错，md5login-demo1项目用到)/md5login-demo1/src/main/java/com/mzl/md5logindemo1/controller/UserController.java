package com.mzl.md5logindemo1.controller;

import com.mzl.md5logindemo1.common.Result;
import com.mzl.md5logindemo1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2020/11/27 18:43
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录验证
     * @param map
     * @param response
     * @return
     */
    @RequestMapping("login")
    public Result login(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response){
        System.out.println(map.get("username"));
        System.out.println(map.get("password"));
        return userService.login(map,request, response);
    }

    /**
     * 用户注册
     * @param map
     * @return
     */
    @RequestMapping("register")
    public Result register(@RequestBody Map<String, Object> map){
        System.out.println(map.get("username"));
        System.out.println(map.get("password"));
        System.out.println(map.get("name"));
        return userService.register(map);
    }

    /**
     * 用户激活
     * @param username
     * @return
     */
    @RequestMapping("active")
    public Result active(String username){
        return userService.active(username);
    }



}
