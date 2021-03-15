package com.mzl.apidesigndemo1.controller;

import com.mzl.apidesigndemo1.annotation.NotRepeatSubmit;
import com.mzl.apidesigndemo1.entity.AccessToken;
import com.mzl.apidesigndemo1.entity.AppInfo;
import com.mzl.apidesigndemo1.entity.TokenInfo;
import com.mzl.apidesigndemo1.entity.UserInfo;
import com.mzl.apidesigndemo1.result.ApiResponse;
import com.mzl.apidesigndemo1.utils.MD5Utils;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   TokenController
 * @Description: token控制器
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:12
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("api/token")
public class TokenController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * API Token(接口令牌): 用于访问不需要用户登录的接口，如登录、注册、一些基本数据的获取等。 获取接口令牌需要拿appId、timestamp和sign来换，
     * sign=加密(timestamp+key)
     * @param appId
     * @param timestamp
     * @param sign
     * @return
     */
    @PostMapping("/apiToken")
    public ApiResponse<AccessToken> apiToken(String appId, @RequestHeader("timestamp") String timestamp, @RequestHeader("sign") String sign){
        Assert.isTrue(!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");

        long requestInterval = System.currentTimeMillis() - Long.valueOf(timestamp);   //时间间隔
        System.out.println(System.currentTimeMillis());
        System.out.println(Long.valueOf(timestamp));
        System.out.println(System.currentTimeMillis() - Long.valueOf(timestamp));
        Assert.isTrue(requestInterval < 5 * 60 * 1000, "请求过期， 请重新请求");

        //1. 根据appId查询数据库获取appSecret密匙
        AppInfo appInfo = new AppInfo("1", "0-123456789");

        //2. 校验签名
        String signString = timestamp + appId + appInfo.getKey();
        String signature = MD5Utils.encode(signString);
        log.info(signature);
        System.out.println("sign:" + signature);
        Assert.isTrue(signature.equals(sign), "签名错误");

        //3.  如果正确生成一个token保存到redis中，如果错误返回错误信息
        AccessToken accessToken = this.saveToken(0, appInfo, null);
        System.out.println(appInfo);

        return ApiResponse.success(accessToken);
    }

    /**
     * USER Token(用户令牌): 用于访问需要用户登录之后的接口，如：获取我的基本信息、保存、修改、删除等操作。获取用户令牌需要拿用户名和密码来换
     * @param username
     * @param password
     * @return
     */
    @NotRepeatSubmit(value = 500000)
    @PostMapping("/userToken")
    public ApiResponse<UserInfo> userToken(String username, String password){
        //根据用户名查询密码，并判断密码是否正确（密码可以RSA加密一下）
        UserInfo userInfo = new UserInfo(username, "2b07748180b1bd2f9dfe1619f5b9fae0", "mzl");
        String pwd = password + userInfo.getSalt();
        String passwordMD5 = MD5Utils.encode(pwd);
        System.out.println("输入密码加盐加密后的密码: " + passwordMD5);
        System.out.println("用户的真正正确的密码：" + userInfo.getPassword());
        Assert.isTrue(passwordMD5.equals(userInfo.getPassword()), "密码错误");

        //保存信息到token
        AppInfo appInfo = new AppInfo("1", "1-123456789");
        AccessToken accessToken = this.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);  //设置用户的有效可用token的有效期
        return ApiResponse.success(userInfo);
    }

    /**
     * 保存USER或API的token
     * @param tokenType
     * @param appInfo
     * @param userInfo
     * @return
     */
    private AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo) {
        String token = UUID.randomUUID().toString();   //为用户或API随机生成一个token作为redis中TokenInfo的key

        //token有效期为2小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);  //设置时间
        Date expireTime = calendar.getTime();

        //token信息设置
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        tokenInfo.setAppInfo(appInfo);

        //只与用户token有关
        if (tokenType == 1){
            tokenInfo.setUserInfo(userInfo);
        }

        //保存用户或Api的token到redis，并设置有效期
        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        operations.set(token, tokenInfo, 7200, TimeUnit.SECONDS);

        AccessToken accessToken = new AccessToken(token, expireTime);

        return accessToken;   //返回用户或api的token令牌的通过成功后有效期
    }


    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String signString = timestamp + "1" + "12345678954556";
        String sign = MD5Utils.encode(signString);
        System.out.println(sign);

        System.out.println("-------------------");
        signString = "password=123456&username=1&12345678954556" + "ff03e64b-427b-45a7-b78b-47d9e8597d3b1529815393153sdfsdfsfs" + timestamp + "A1scr6";
        sign = MD5Utils.encode(signString);
        System.out.println(sign);
    }

}
