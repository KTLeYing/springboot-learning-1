package com.mzl.updateimage.service.impl;

import com.mzl.updateimage.entity.*;
import com.mzl.updateimage.dao.User1Mapper;
import com.mzl.updateimage.result.Result;
import com.mzl.updateimage.result.ResultCode;
import com.mzl.updateimage.service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User1ServiceImpl implements User1Service {

    @Autowired
    private User1Mapper user1Mapper;

    /**
     * 更新用户头像
     * @param user
     */
    @Override
    public int updateUser(User1 user) {
        return user1Mapper.updateUser(user);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public Result login(String username, String password) {
        User1 user1 = user1Mapper.login(username, password);
        System.out.println(user1);
        if (user1 != null){
            return Result.success(user1);
        }else {
            return new Result(ResultCode.USER_NOT_EXISTS.getCode(), ResultCode.USER_NOT_EXISTS.getMsg());
        }
    }

}