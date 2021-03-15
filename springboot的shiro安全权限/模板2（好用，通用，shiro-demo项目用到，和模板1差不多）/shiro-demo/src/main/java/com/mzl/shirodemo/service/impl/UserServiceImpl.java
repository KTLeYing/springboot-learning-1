package com.mzl.shirodemo.service.impl;

import com.mzl.shirodemo.service.UserService;
import com.mzl.shirodemo.shiro.AccountProfile;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑接口实现类
 * @Author: mzl
 * @CreateDate: 2020/9/16 19:47
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 调用对应Realm进行登录校验，认证成功则返回用户属性，失败则抛出对应异常
     * @param username
     * @param password
     * @return
     */
    @Override
    public AccountProfile login(String username, String password) {
        //查询数据库，匹配信息看是否正确
        if(!"mzl".equals(username)) {
            // 抛出shiro异常，方便通知用户登录错误信息
            throw new UnknownAccountException("用户不存在");
        }
        if(!"88888".equals(password)) {
            // 抛出shiro异常，方便通知用户登录弥漫着错误
            throw new IncorrectCredentialsException("密码错误");
        }

        //登录信息正确，保存用户
        AccountProfile accountProfile = new AccountProfile();
        accountProfile.setId(1L);
        accountProfile.setUsername("MZL");
        accountProfile.setGender("男");
        accountProfile.setSign("马振乐和郭倩盈一起努力！");

        return accountProfile;
    }

}
