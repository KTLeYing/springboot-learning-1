package com.mzl.jwtdemo3.controller;

import com.mzl.jwtdemo3.constant.CommonConstant;
import com.mzl.jwtdemo3.dto.UserLoginDto;
import com.mzl.jwtdemo3.result.ModelResult;
import com.mzl.jwtdemo3.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   JwtController
 * @Description: Jwt测试控制器
 * @Author: mzl
 * @CreateDate: 2020/10/11 12:21
 * @Version: 1.0
 */
@RestController
@RequestMapping("jwt")
public class JwtController {

    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    @RequestMapping("/login")
    public ModelResult<String> login(UserLoginDto userLoginDto){
        System.out.println(userLoginDto);
        ModelResult<String> result = new ModelResult<>();
        //简单模拟登录
        if (userLoginDto.getUsername().equals("mzl") && userLoginDto.getPassword().equals("88888")){
            Map<String, Object> userInfoMap = new HashMap<>();
            userInfoMap.put("username", userLoginDto.getUsername());
            String token = JwtUtils.generateToken(userInfoMap);
            System.out.println(token);
            result.setData(token);
            result.setMsg("登录成功");
            result.setCode(CommonConstant.SUCCESS);
        }else {
            result.setMsg("用户名或密码错误");
            result.setCode(CommonConstant.FAIL);
        }

        System.out.println(result);
        return result;
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("/userInfo")
    public Object userInfo(HttpServletRequest request){
        //登录成功后从token(转为claim再来获取)中获取用户信息
//        Claims claims = (Claims) request.getAttribute("username");

        String token = request.getHeader(JwtUtils.getHeaderKey());
        Claims claims = JwtUtils.verifyAndGetClaimsByToken(token);
        System.out.println("claims:" + claims);
        if (claims != null){
            return claims;
        }else {
            return "fail";
        }
    }



}
