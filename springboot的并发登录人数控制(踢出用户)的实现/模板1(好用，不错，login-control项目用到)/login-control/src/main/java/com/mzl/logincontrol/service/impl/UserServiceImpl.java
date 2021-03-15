package com.mzl.logincontrol.service.impl;

import com.mzl.logincontrol.pojo.UserBO;
import com.mzl.logincontrol.util.JWTUtil;
import com.mzl.logincontrol.service.UserService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String buildUserInfo(UserBO user) {
        System.out.println("创建用户信息...");
        String username = user.getUsername();
        String jwt = JWTUtil.sign(username, JWTUtil.SECRET);
        System.out.println("jwt:" + jwt);
        Assert.notNull(jwt, "jwt cannot null");
        RBucket rBucket = redissonClient.getBucket(jwt);
        //设置用户redisssion，使用redission的RBucket来设置（像普通的redis类似的）
        rBucket.set(user, JWTUtil.EXPIRE_TIME_MS, TimeUnit.MILLISECONDS);
        return jwt;
    }

    @Override
    public void logout(String jwt) {
        RBucket rBucket = redissonClient.getBucket(jwt);
        rBucket.delete();
    }
}
