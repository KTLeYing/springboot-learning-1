package com.mzl.SSOdemo1.service.impl;

import com.google.common.base.Joiner;
import com.mzl.SSOdemo1.redis.RedisUtil;
import com.mzl.SSOdemo1.redis.UserKey;
import com.mzl.SSOdemo1.service.AuthTokenService;
import com.mzl.SSOdemo1.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @ClassName :   Au
 * @Description: 认证token的业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/1/30 10:30
 * @Version: 1.0
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取用户token
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public String getToken(String userId) throws Exception {
        System.out.println("进行获取用户token...");
        //加密生成用户的token
        String token = AESUtil.encryptByDefaultKey(Joiner.on("_").join(userId, System.currentTimeMillis()));
        System.out.println("token={0}==>" + token);
        //设置用户的登录的redis
        redisUtil.set(UserKey.userAccessKey, userId, token);
        return token;
    }

    /**
     * 校验用户的token
     * @return
     * @throws Exception
     */
    @Override
    public String checkToken(String token) throws Exception {
        System.out.println("进行校验用户的token...");
        //解密用户的token,从token中获取用户名/用户ID（用户唯一标准）
        String userId = AESUtil.decryptByDefaultKey(token).split("_")[0];
        String currentToken = redisUtil.get(UserKey.userAccessKey, userId, String.class);
        System.out.println("currentToken={0}==>" + currentToken);
        //判断redis的token是否存在
        if (StringUtils.isEmpty(currentToken)){  //redis不存在token（可能过期或没有登录过）
            return null;
        }
        //校验请求的token是否正确
        if (!token.equals(currentToken)){   //请求的不正确
            return null;
        }

        //否则认证成功
        return userId;
    }


}
