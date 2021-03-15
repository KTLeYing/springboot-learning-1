package com.mzl.activeuserdemo1.service;

import com.mzl.activeuserdemo1.pojo.User;

/**
 * @InterfaceName :   UserService
 * @Description: T用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/11/27 21:22
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    Boolean register(User user);

    /**
     * 校验邮箱中的code激活账户
     * @param code
     * @return
     */
    User checkCode(String code);

    /**
     * 更新用户激活状态
     * @param user
     */
    void updateUserStatus(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);
}
