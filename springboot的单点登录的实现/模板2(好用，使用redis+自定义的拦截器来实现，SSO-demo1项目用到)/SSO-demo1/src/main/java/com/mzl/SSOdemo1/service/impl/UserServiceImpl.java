package com.mzl.SSOdemo1.service.impl;

import com.mzl.SSOdemo1.entity.User;
import com.mzl.SSOdemo1.result.ResultMap;
import com.mzl.SSOdemo1.service.AuthTokenService;
import com.mzl.SSOdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/1/30 9:55
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthTokenService authTokenService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public ResultMap login(User user) throws Exception {
        if (user.getUsername().equals("mzl") && user.getPassword().equals("88888")){
            return new ResultMap(200, "登录成功", authTokenService.getToken(user.getUsername()));
        }
        return new ResultMap(401, "登录失败", null);
    }

    /**
     * 单点登录的测试
     * @return
     * @throws Exception
     */
    @Override
    public ResultMap testSSO() throws Exception {
        return new ResultMap(200, "登录状态正常，不需要再次登录", true);
    }


}
