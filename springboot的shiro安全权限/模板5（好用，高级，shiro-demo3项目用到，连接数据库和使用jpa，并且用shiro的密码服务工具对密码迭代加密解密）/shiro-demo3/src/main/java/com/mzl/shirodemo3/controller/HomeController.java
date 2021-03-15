package com.mzl.shirodemo3.controller;

import com.mzl.shirodemo3.entity.User;
import com.mzl.shirodemo3.service.UserService;
import com.mzl.shirodemo3.shiro.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   HomeController
 * @Description: 登录的通用的控制器
 * @Author: mzl
 * @CreateDate: 2020/9/24 20:05
 * @Version: 1.0
 */
@RestController
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 为认证，跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public Object login(){
        return "Here is Login page!";
    }

    /**
     * 跳转到未授权页面
     * @return
     */
    @RequestMapping("/unauthc")
    public Object unauthc(){
        return "Here is Unauthc page!";
    }

    /**
     * 登录操作处理
     * @return
     */
    @RequestMapping("/doLogin")
    public Object doLogin(String username, String password){
        //获取用户令牌对象
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //创建安全机制的字体
        Subject subject = SecurityUtils.getSubject();
        try {
            //subject携带token令牌去给安全管理机制去认证和授权
            subject.login(token);
        } catch (Exception e) {
            if (e instanceof UnknownAccountException){
                return "username error!";
            }else if (e instanceof IncorrectCredentialsException){
                return "password error!";
            }else {
                return "authentication failed!";
            }
        }

        return "登录成功SUCCESS...";
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/register")
    public Object register(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        //对用户密码进行加密
        passwordHelper.encryptPassword(user);

        //添加用户
        userService.saveUser(user);
        return "用户注册成功！";
    }

    /**
     * 注销操作
     * @return
     */
    @RequestMapping("/logout")
    public Object logout(){
        //使用安全管理的工具类的logout方法来清除用户的所有的信息和跟踪
        SecurityUtils.getSubject().logout();
        return "注销成功...";
    }

}
