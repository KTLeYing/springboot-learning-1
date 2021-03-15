package com.mzl.jwtdemo2.controller;

/**
 * @ClassName :   JWTController
 * @Description: jwt控制器
 * @Author: mzl
 * @CreateDate: 2020/10/9 21:26
 * @Version: 1.0
 */

import com.mzl.jwtdemo2.utils.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {

    /**
     * 模拟登录，假如用户名和密码都是正确的，生成token，用用户名作为用户的唯一标识id
     * @param username
     * @return
     */
    @RequestMapping("/login")
    public String login(String username){
        String JWT = JwtUtil.createJWT(username);
        return "登录成功!\t" + JWT;
    }

    @RequestMapping("/verifyToken")
    public String verifyToken(String token, String username){
        boolean flag = JwtUtil.verify(token, username);
        if (flag){
            return "token正确，可以访问";
        }else {
            return "token错误，不能访问";
        }
    }

    /**
     * 无需SECRET，直接获取用户信息
     * @param token
     * @return
     */
    @RequestMapping("/findUserInfo")
    public String findUserInfo(String token){
        return JwtUtil.getUserCode(token);
    }

}
