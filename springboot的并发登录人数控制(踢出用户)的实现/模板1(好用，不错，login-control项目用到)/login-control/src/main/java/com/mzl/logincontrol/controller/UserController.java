package com.mzl.logincontrol.controller;

import com.mzl.logincontrol.pojo.ApiResult;
import com.mzl.logincontrol.pojo.CurrentUser;
import com.mzl.logincontrol.pojo.UserBO;
import com.mzl.logincontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userBO
     * @return
     */
    @PostMapping("/login")
    public ApiResult login(@RequestBody UserBO userBO) {
        System.out.println("执行了登录接口...");
        System.out.println(userBO);
        return new ApiResult(200, "登录成功", userService.buildUserInfo(userBO));
    }

    /**
     * 获取用户登录后的信息
     * @return
     */
    @GetMapping("/user/getInfo")
    public ApiResult getInfo() {
        System.out.println("执行了获取用户信息接口...");
        UserBO user = CurrentUser.get();
        System.out.println(user);
        return new ApiResult(200, null, user);
    }

    /**
     * 登出操作
     * @param jwt
     * @return
     */
    @PostMapping("/logout")
    public ApiResult logout(@RequestHeader("Authorization") String jwt) {
        userService.logout(jwt);
        return new ApiResult(200, "成功", null);
    }

}

