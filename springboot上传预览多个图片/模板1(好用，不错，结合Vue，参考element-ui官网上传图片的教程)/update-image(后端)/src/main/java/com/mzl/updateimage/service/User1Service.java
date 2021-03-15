package com.mzl.updateimage.service;

import com.mzl.updateimage.entity.User1;
import com.mzl.updateimage.result.Result;

/**
 * @InterfaceName :   UserService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2021/1/2 11:21
 * @Version: 1.0
 */
public interface User1Service {

    /**
     * 更新用户头像
     * @param user
     */
    int updateUser(User1 user);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Result login(String username, String password);
}
