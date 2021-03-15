package com.mzl.shirojwtSSO.controller;

import com.mzl.shirojwtSSO.annotation.SSOFlag;
import com.mzl.shirojwtSSO.entity.User;
import com.mzl.shirojwtSSO.result.ResultMap;
import com.mzl.shirojwtSSO.service.UserService;
import com.mzl.shirojwtSSO.shiro.jwt.JwtUtil;
import javafx.scene.chart.ValueAxis;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSetMetaData;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:36
 * @Version: 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public ResultMap login(User user){
        User user1 = userService.findUser(user.getUsername());
        if (user1 == null){
            return new ResultMap(500, "用户名错误", null);
        }
        if (!user1.getPassword().equals(user.getPassword())){
            return new ResultMap(501, "密码错误", null);
        }
        //否则用户名和密码都正确, 生成并返回token给用户用于后面的其他请求
        String token = JwtUtil.createToken(user1.getUsername());
        //设置用户的token的redis(5分钟有效期)
        stringRedisTemplate.opsForValue().set("token:" + user.getUsername(), token, 300, TimeUnit.SECONDS);
        return new ResultMap(200, "登录成功", token);
    }

    /**
     * 无权限跳转的接口
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(path = "/unauthorized/{message}")
    public ResultMap unauthorized(@PathVariable String message) {
        System.out.println("执行了身份认证失败或无权限跳转的接口...");
        return new ResultMap(401, message, null);
    }

    /**
     * 不需要权限的普通测试
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/test")
    public ResultMap test() throws UnsupportedEncodingException {
        return new ResultMap(200, "恭喜你可以进行访问", null);
    }

    /**
     * 角色测试，拥有 admin 角色的用户才可以访问下面的页面
     * @return
     */
    @RequestMapping("/roleTest")
    @RequiresRoles(value = "admin")
    public ResultMap roleTest(){
        return new ResultMap(200, "你有该角色，可以访问", true);
    }

    /**
     * 角色测试，同时拥有 admin 和 user 角色的用户才可以访问下面的页面
     * @return
     */
    @RequestMapping("/roleTest1")
    @RequiresRoles(value = {"admin", "user"})
    public ResultMap roleTest1(){
        return new ResultMap(200, "你有该角色，可以访问", true);
    }

    /**
     * 权限测试，拥有add权限才能访问
     * @return
     */
    @RequestMapping("/permissionTest")
    @RequiresPermissions(value = "delete")
    public ResultMap permissionTest(){
        return new ResultMap(200, "你有该权限，可以访问", true);
    }

    /**
     * 权限测试，同时拥有add 和 delete 权限才能访问
     * @return
     */
    @RequestMapping("/permissionTest1")
    @RequiresPermissions(value = {"query", "add"})
    public ResultMap permissionTest1(){
        return new ResultMap(200, "你有该权限，可以访问", true);
    }

    /**
     * 角色和权限同时测试，同时拥有admin角色，且拥有query 和 add 权限才能访问
     * @return
     */
    @RequestMapping("/roleAndPermissionTest")
    @RequiresRoles(value = {"admin"})
    @RequiresPermissions(value = {"query", "add"})
    public ResultMap roleAndPermissionTest(){
        return new ResultMap(200, "你有该权限，可以访问", true);
    }

    /**
     * 模拟单点登录测试
     * 可以直接使用shiro的jwt的身份认证（authentication）来实现，只要不抛出异常就说明是单点登录成功，无需再次登录；否则需要再次登录
     * @param
     * @return
     */
    @RequestMapping("/testSSO")
    @SSOFlag
    public ResultMap testSSO(){
        return new ResultMap(200, "登录状态正常，用户处于登录中，无需再次登录，单点登录成功！", true);
    }

}
