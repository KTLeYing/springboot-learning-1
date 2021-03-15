package com.mzl.shirojwt.controller;

import com.mzl.shirojwt.Result.ResponseResult;
import com.mzl.shirojwt.entity.User;
import com.mzl.shirojwt.service.UserService;
import com.mzl.shirojwt.util.StringUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:24
 * @Version: 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 统一报错接口(没有认证)
     * @return
     */
    @RequestMapping("/401")
    public ResponseResult login401(){
        return new ResponseResult(401, "Authc ERROR", false);
    }

    /**
     * 用户认证的测试
     * @return
     */
    @RequestMapping("/authcTest")
    public ResponseResult authcTest(){
        return new ResponseResult(200, "恭喜你认证成功", true);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletResponse httpServletResponse){
        if (StringUtil.isBlank(user.getAccount() ) || StringUtil.isBlank(user.getPassword()) ){
            return new ResponseResult(0, "用户名或密码错误", false);
        }
        return userService.login(user,httpServletResponse);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public ResponseResult register(User user){
        userService.register(user);
        return new ResponseResult(200, "注册成功(Regitser Success)", null);
    }

    /**
     * 查询用户列表(角色的测试)
     * @return
     */
    @RequestMapping("/userList")
    //--通过角色来控制访问权限。管理员可以访问(可以用注解在请求接口上配置权限和角色，也可以在shiro配置类中用filterChainDefinitionMap来配置)
    @RequiresRoles(value = "admin")
    public ResponseResult userList() {
        return userService.userList();
    }

    /**
     * 查询用户自己的信息(角色的测试)
     * @return
     */
    @RequestMapping("/userInfo")
    //--通过普通用户来控制访问权限。普通可以访问(可以用注解在请求接口上配置权限和角色，也可以在shiro配置类中用filterChainDefinitionMap来配置)
    @RequiresRoles(value = "user")
    public ResponseResult userInfo(HttpServletRequest httpServletRequest) {
        return userService.userInfo(httpServletRequest);
    }

    /**
     * 查询用户自己的信息(多个角色的测试)
     * @return
     */
    @RequestMapping("/moreRoles")
    //--通过普通用户来控制访问权限。普通可以访问(可以用注解在请求接口上配置权限和角色，也可以在shiro配置类中用filterChainDefinitionMap来配置)
    //要求subject中必须同时含有user和admin的角色才能执行方法someMethod()。否则抛出异常
    @RequiresRoles(value = {"user", "admin"})
    public ResponseResult moreRoles(HttpServletRequest httpServletRequest) {
        return new ResponseResult(100,"你所在的权限拥有该操作权限  ",true);
    }

    /**
     * 权限的测试（单个权限的测试）
     * @return
     */
    @RequestMapping("/testPermission")
    @RequiresPermissions(value = "add")  //可以用注解在请求接口上配置权限和角色，也可以在shiro配置类中用filterChainDefinitionMap来配置
    public ResponseResult testPermission(){
        return new ResponseResult(100,"你所在的权限拥有  {user:add} 页面权限  ",true);
    }

    /**
     * 权限的测试（单个权限的测试）
     * @return
     */
    @RequestMapping("/testPermission1")
    @RequiresPermissions("delete")  //可以用注解在请求接口上配置权限和角色，也可以在shiro配置类中用filterChainDefinitionMap来配置
    public ResponseResult testPermission1(){
        return new ResponseResult(100,"你所在的权限拥有  {user:delete} 页面权限  ",true);
    }

    /**
     * 权限的测试（多个权限的测试）
     * @return
     */
    @RequestMapping("/testPermission2")
    //要求subject中必须同时含有add和delete的权限才能执行方法someMethod()。否则抛出异常
    @RequiresPermissions(value = {"delete", "add"})  //可以用注解在请求接口上配置权限和角色，也可以在shiro配置类中用filterChainDefinitionMap来配置
    public ResponseResult testPermission2(){
        return new ResponseResult(100,"你所在的权限拥有该操作的权限  ",true);
    }

    /**
     * 权限的测试（多个权限的测试）
     * @return
     */
    @RequestMapping("/testPermission3")
    //要求subject中必须同时含有query和delete的权限才能执行方法someMethod()。否则抛出异常
    @RequiresPermissions({"query", "delete"})  //可以用注解在请求接口上配置权限和角色，也可以在shiro配置类中用filterChainDefinitionMap来配置
    public ResponseResult testPermission3(){
        return new ResponseResult(100,"你所在的权限拥有该操作的权限  ",true);
    }

}
