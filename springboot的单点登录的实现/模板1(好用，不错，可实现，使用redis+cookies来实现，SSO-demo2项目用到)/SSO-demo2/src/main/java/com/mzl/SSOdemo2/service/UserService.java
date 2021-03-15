package com.mzl.SSOdemo2.service;

import com.mzl.SSOdemo2.entity.User;
import com.mzl.SSOdemo2.result.ResultMap;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑实现接口
 * @Author: mzl
 * @CreateDate: 2021/2/8 0:28
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    ResultMap login(User user, HttpServletRequest request, HttpServletResponse response);

    /**
     *
     * @param user
     * @return
     */
    ResultMap register(User user);

    /**
     * 用户登出
     * @param token
     * @param request
     * @param response
     * @return
     */
    ResultMap logout(String token, HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取用户的token的值（用户的信息）
     * @param token
     * @return
     */
    ResultMap getToken(String token);
}
