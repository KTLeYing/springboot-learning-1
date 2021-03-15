package com.mzl.jwtdemo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.mzl.jwtdemo1.config.JwtConfig;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ClassName :   TokenController
 * @Description: JWT测试接口控制器
 * @Author: mzl
 * @CreateDate: 2020/10/9 19:06
 * @Version: 1.0
 */
@RestController
public class TokenController {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public Object login(String username, String password){
        JSONObject jsonObject = new JSONObject();   //Json的底层原理是Map，所以它的用法与Map相似，key-value的形式

        //这里模拟用户登录，从数据库中查询判断,现在不做用户名和密码的判断，假如密码和用户名都是正确的
        // 这里把userId转为String类型，实际开发中如果subject需要存userId，则可以JwtConfig的createToken方法的参数设置为Long类型
        String userId = 5 + "";
        String token = jwtConfig.createToken(userId);
        if (!StringUtils.isEmpty(token)){
            jsonObject.put("token", token);
            jsonObject.put("msg", "登录成功");
        }

        return jsonObject;
    }

    /**
     * 其他请求路径，要进行token验证的接口
     * @return
     */
    @RequestMapping("/getInfo")
    public Object getInfo(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "token正确，可以访问！");

        try{
            String usernameFromToken = jwtConfig.getUsernameFromToken(request.getHeader("token"));
            if (StringUtils.isEmpty(usernameFromToken)){
                jsonObject.put("msg", "token不正确或有异常，不可以访问！");
            }

            Claims claims = jwtConfig.getTokenClaim(request.getHeader("token"));
            jsonObject.put("claims", claims);
            Date getExpirationDateFromToken = jwtConfig.getExpirationDateFromToken(request.getHeader("token"));
            jsonObject.put("getExpirationDateFromToken", getExpirationDateFromToken);
            Date getIssueAtDateFromToken = jwtConfig.getIssueAtDateFromToken(request.getHeader("token"));
            jsonObject.put("getIssueAtDateFromToken", getIssueAtDateFromToken);

        } catch (Exception e) {
            jsonObject.put("msg", "token不正确或有异常，不可以访问！");
            e.printStackTrace();
        }

        return jsonObject;
    }



}
