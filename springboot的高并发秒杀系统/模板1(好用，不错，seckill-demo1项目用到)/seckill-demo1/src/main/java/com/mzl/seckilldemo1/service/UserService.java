package com.mzl.seckilldemo1.service;

import com.mzl.seckilldemo1.dto.LoginDTO;
import com.mzl.seckilldemo1.dto.UserDTO;
import com.mzl.seckilldemo1.entity.User;

import javax.servlet.http.HttpServletResponse;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/3/1 17:48
 * @Version: 1.0
 */
public interface UserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    /**
     * 通过token获取用户信息
     * @param response
     * @param token
     * @return
     */
    User getByToken(HttpServletResponse response, String token);

    /**
     * 用户登录
     * @param response
     * @param loginDTO
     * @return
     */
    String login(HttpServletResponse response, LoginDTO loginDTO);

    /**
     * 用户注册
     * @param user
     * @return
     */
    int register(UserDTO user);
}
