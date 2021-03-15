package com.mzl.shirojwt.service;

import com.mzl.shirojwt.Result.ResponseResult;
import com.mzl.shirojwt.entity.User;
import org.apache.shiro.authc.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:26
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 用户登录
     * @param user
     * @param httpServletResponse
     * @return
     */
    ResponseResult login(User user, HttpServletResponse httpServletResponse);

    /**
     * 查询用户列表
     * @return
     */
    ResponseResult userList();

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 查询用户自己的信息(角色的测试)
     * @return
     */
    ResponseResult userInfo(HttpServletRequest httpServletRequest);
}
