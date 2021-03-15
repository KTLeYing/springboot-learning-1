package com.mzl.apidesigndemo1.interceptor;

import com.mzl.apidesigndemo1.annotation.NotRepeatSubmit;
import com.mzl.apidesigndemo1.entity.TokenInfo;
import com.mzl.apidesigndemo1.utils.ApiUtils;
import com.mzl.apidesigndemo1.utils.MD5Utils;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   TokenInterceptor
 * @Description: token拦截器
 * @Author: mzl
 * @CreateDate: 2020/12/19 17:16
 * @Version: 1.0
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 拦截前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String timestamp = request.getHeader("timestamp");
        //随机字符串
        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        //如果expression为false就会抛出参数非法的异常
        Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");

        //1. 获取超时时间
        NotRepeatSubmit notRepeatSubmit = ApiUtils.getNotRepeatSubmit(handler);
        long expireTime = notRepeatSubmit == null? 5 * 60 * 1000 : notRepeatSubmit.value();
        System.out.println(expireTime);

        //2. 请求时间间隔
        long requestInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
        System.out.println(System.currentTimeMillis());
        System.out.println(Long.valueOf(timestamp));
        System.out.println(requestInterval);
        Assert.isTrue(requestInterval < expireTime, "请求超时，请重新连接");

        //3. 校验token是否存在
        ValueOperations<String, TokenInfo> tokenRedis = redisTemplate.opsForValue();
        TokenInfo tokenInfo = tokenRedis.get(token);
        System.out.println("redis中的tokenInfo： " + tokenInfo);
        Assert.notNull(token, "token错误");

        //4. 校验签名（将所有的参数都加进来，防止别人篡改参数）， 所有的参数按照参数名升序排序拼接成url
        //请求参数 = token + timestamp + nonce
        System.out.println("iiiii");
        String signString = ApiUtils.concatSignString(request) + tokenInfo.getAppInfo().getKey() + token + timestamp + nonce;
        System.out.println(ApiUtils.concatSignString(request));
        System.out.println(tokenInfo.getAppInfo().getKey());
        System.out.println(token);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(signString);
        String signature = MD5Utils.encode(signString);   //对签名字符串进行加密（每一次提交都不同，因为timestamp的影响）
        System.out.println("加密的signature： " + signature);
        boolean flag = signature.equals(sign);
        Assert.isTrue(flag, "签名错误");

        //5. 拒绝重复调用(第一次访问时存储，过期时间和请求超时时间保持一致), 只有标注不允许重复提交注解的接口才会校验
        if (notRepeatSubmit != null){
            ValueOperations<String, Integer> signRedis = redisTemplate.opsForValue();
            boolean exist = redisTemplate.hasKey(sign);
            System.out.println("exist:" + exist);
            Assert.isTrue(!exist, "你已提交过，请勿重复提交");
//            signRedis.set(sign, 0, expireTime, TimeUnit.MILLISECONDS);
            signRedis.set(sign, 0, 50000, TimeUnit.MILLISECONDS); //用户令牌成功通过后，使用用户的签名作为key设置redis避免用户频繁重复提交
        }

        return super.preHandle(request, response, handler);    //preHandle()方法返回值为boolean
    }

    /**
     * 执行完接口后执行该方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("已经执行完了接口...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
