package com.mzl.SSOdemo1.service;

import com.mzl.SSOdemo1.entity.User;
import com.mzl.SSOdemo1.result.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/1/30 9:55
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    ResultMap login(User user) throws Exception;

    /**
     * 单点登录的测试
     * @return
     */
    ResultMap testSSO() throws Exception;
}
