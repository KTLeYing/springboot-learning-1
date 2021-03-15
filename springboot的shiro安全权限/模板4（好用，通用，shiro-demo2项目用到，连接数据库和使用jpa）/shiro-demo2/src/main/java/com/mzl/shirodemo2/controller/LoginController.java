package com.mzl.shirodemo2.controller;

import com.mzl.shirodemo2.service.UserService;
import com.mzl.shirodemo2.utils.ResultMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.java2d.pipe.SolidTextRenderer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @ClassName :   LoginController
 * @Description: 用户登录控制器
 * @Author: mzl
 * @CreateDate: 2020/9/23 9:53
 * @Version: 1.0
 */
@RestController
public class LoginController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public ResultMap login(String username, String password){
        System.out.println(username + " " + password);
        //1.先从安全管理工具类里获取处理主体Subject单例对象
        Subject subject = SecurityUtils.getSubject();
        //2.在认证提交前准备 token（令牌），生成一张命里牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //3.执行认证登
        try{
            subject.login(token);
        } catch (AccountException e) {
            return resultMap.fail().message("用户名或密码错误！");
        }

        //根据权限，指定返回数据
        String role = userService.findUserByUsername(username).getRole();
        System.out.println(role);
        if (role.equals("user")){
            return resultMap.success().message("欢迎登录！");
        }else if (role.equals("admin")){
            return resultMap.success().message("欢迎来到管理员页面！");
        }
        return resultMap.fail().message("权限错误！");
    }

    /**
     * 用户尚未登录接口
     * @return
     */
    @RequestMapping("/notLogin")
    public ResultMap notLogin(){
        return resultMap.success().message("您尚未登录！");
    }

    /**
     * 用户没有操作权限接口
     * @return
     */
    @RequestMapping("/notRole")
    public ResultMap notRole(){
        return resultMap.success().message("您没有权限！");
    }

    /**
     * 退出登录接口
     * @return
     */
    @RequestMapping("/logout")
    public ResultMap logout(){
        //从安全管理工具类中获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //使用主体对象来登出
        subject.logout();
        return resultMap.success().message("成功注销！");
    }


}
