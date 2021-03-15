package com.mzl.shirodemo2.service;

import com.mzl.shirodemo2.entity.User;

/**
 * @InterfaceName :   Userservice
 * @Description: 用户业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2020/9/21 21:14
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
