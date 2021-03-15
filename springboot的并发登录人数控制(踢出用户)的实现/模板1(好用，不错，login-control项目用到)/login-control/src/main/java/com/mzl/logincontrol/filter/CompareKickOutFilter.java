package com.mzl.logincontrol.filter;

import com.mzl.logincontrol.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 踢出方式二：比较时间戳踢出方式
 */
public class CompareKickOutFilter extends KickOutFilter {
    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行比较时间戳方式踢出...");
        String token = request.getHeader("Authorization");
        System.out.println("token:" + token);
        String username = JWTUtil.getUsername(token);
        String userKey = PREFIX + username;
        System.out.println("userKey：" + userKey);

        //类似redis的操作，这里是redission来的，获取user对应的redission的bucket对象
        RBucket<String> bucket = redissonClient.getBucket(userKey);
        //获取redis中用户对应的token数据值
        String redisToken = bucket.get();
        System.out.println("redisToken:" + redisToken);

        if (token.equals(redisToken)) {   //token相同则表明为同一个人登录，放行
            System.out.println("token正确且未过期，同一个token用户来的...");
            return true;
        } else if (StringUtils.isBlank(redisToken)) {  //redis的token为空，表示以前的登录过期了，放行
            System.out.println("redis的token为空，表示以前的登录过期了");
            bucket.set(token);
        } else {  //redis的token不为空，则判断时间戳，看是否在其他的设备上登录了
            Long redisTokenUnixTime = JWTUtil.getClaim(redisToken, "createTime").asLong();
            Long tokenUnixTime = JWTUtil.getClaim(token, "createTime").asLong();

            System.out.println("kkk");
            System.out.println(redisTokenUnixTime);
            System.out.println(tokenUnixTime);

            // token > redisToken 则覆盖(默认生成的时间戳大于之前redis的有效时间戳，重新设置用户的token，但redis有效时间不变的，覆盖它，放行请求)
            if (tokenUnixTime.compareTo(redisTokenUnixTime) > 0) {
                System.out.println("默认生成的时间戳大于之前redis的有效时间戳，重新设置用户的token");
                bucket.set(token);
            } else {
                //token < redisToken表示通过新设备的用户重新登录后的token的redis时间戳的更新了，比之前一次的时间戳要大，已在其他的设备上登录了（踢出之前一次用户）
                //已在其他设备登录了， 注销当前的token，拦截请求
                userService.logout(token);
                System.out.println("您的账号已在其他设备登录,注销该用户");
                sendJsonResponse(response, 4001, "您的账号已在其他设备登录");
                return false;
            }
        }

        return true;
    }
}
