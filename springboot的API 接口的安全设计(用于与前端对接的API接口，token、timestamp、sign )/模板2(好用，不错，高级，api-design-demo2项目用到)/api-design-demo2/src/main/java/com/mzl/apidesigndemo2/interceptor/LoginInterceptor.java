package com.mzl.apidesigndemo2.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mzl.apidesigndemo2.uitl.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   LoginInterceptor
 * @Description: 登录拦截器
 * @Author: mzl
 * @CreateDate: 2021/2/1 22:49
 * @Version: 1.0
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行了接口请求的拦截器...");

        JSONObject result = new JSONObject();
        System.out.println("判断请求参数是否合法...");
        //获取请求认证的参数（请求参数的方式）
//        String token = request.getParameter("token");  //用户请求的token
//        String sign = request.getParameter("sign");   //用户请求的sign
//        String timestamp = request.getParameter("timestamp");   //用户请求的时间戳
        //获取请求认证的参数（请求头的方式）
        String token = request.getHeader("token");  //用户请求的token
        String sign = request.getHeader("sign");   //用户请求的sign
        String timestamp = request.getHeader("timestamp");   //用户请求的时间戳
        //判断请求参数是否合法（三个参数都不能为空）
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp)){
            System.out.println("请求参数不合法");
            result.put("success",  false);
            result.put("code", 1001);
            result.put("msg", "请求参数不能为空");
            request.getRequestDispatcher("/user/error/" + result.toJSONString()).forward(request, response);
//            PrintWriter printWriter = response.getWriter();
//            printWriter.write(result.toJSONString());
            return false;  //拦截请求
        }

        //判断redis的token是否为空且是否为真正最新的token，如果redis存在token且为最新一次登录生成的最新token就认为是合法的请求
        if (redisTemplate.hasKey(token)){
            long expireTime =  redisTemplate.opsForValue().getOperations().getExpire(token);
            System.out.println("token的有效时间:" + expireTime);   //获取用户token的有效时间
            String value = (String) redisTemplate.opsForValue().get(token);  //获取用户token的值（用户名/用户id）
            System.out.println("token的值：" + value);
            System.out.println("判断token是否即将过期，进行续命操作...");
            //判断token是否即将过期，进行续命操作
            if (expireTime != -2 && expireTime < 20){
                //如果token的redis存在且有效时间小于20秒，则进行重置redis的token的有效时间为10分钟
                redisTemplate.opsForValue().set(token, value, 10L, TimeUnit.MINUTES);
            }

            System.out.println("判断是否重复访问...");
            //获取当前的时间戳
            long nowTimestamp = SignUtils.getTimestamp();
            System.out.println("当前时间戳：" + nowTimestamp);
            // 判断是否重复访问，存在重复攻击的时间窗口期（引入一个时间戳参数，保证接口仅在一分钟内有效，需要和客户端时间保持一致，
            // 需要跟当前服务器时间进行对比，如果超过一分钟，就拒绝本次请求，要重新登录获取新的token，节省服务器查询数据的消耗）
            System.out.println("访问的时间戳的差值：" + (nowTimestamp - Long.valueOf(timestamp)));
            if ((nowTimestamp - Long.valueOf(timestamp)) > 600){
                System.out.println("重复访问了，连接服务器超时");
                result.put("success",  false);
                result.put("code", 1002);
                result.put("msg", "连接服务器超时");
                request.getRequestDispatcher("/user/error/" + result.toJSONString()).forward(request, response);
//                PrintWriter printWriter = response.getWriter();
//                printWriter.write(result.toJSONString());
                return false;
            }

            System.out.println("判断签名是否正确（验证签名）...");
            //验证签名
            if (!SignUtils.checkSign(request, sign)){
                System.out.println("签名错误");
                result.put("success",  false);
                result.put("code", 1003);
                result.put("msg", "签名错误");
                request.getRequestDispatcher("/user/error/" + result.toJSONString()).forward(request, response);
//                PrintWriter printWriter = response.getWriter();
//                printWriter.write(result.toJSONString());
                return false;
            }

            //请求的三个参数验证都正确了，才放行请求
            return true;
        }else {   //用户的token的redis不存在（过期或者没登录过）
            System.out.println("token无效或错误，请重新登录");
            result.put("success",  false);
            result.put("code", 1004);
            result.put("msg", "token无效或错误，请重新登录");
            request.getRequestDispatcher("/user/error/" + result.toJSONString()).forward(request, response);
//            PrintWriter printWriter = response.getWriter();
//            printWriter.write(result.toJSONString());
            return false;
        }
    }

}
