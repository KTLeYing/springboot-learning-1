package com.mzl.authcssodemo.service;

import com.alibaba.fastjson.JSONObject;
import com.mzl.authcssodemo.entity.User;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2021/2/1 22:40
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    JSONObject login(User user);

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
