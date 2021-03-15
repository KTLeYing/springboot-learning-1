package com.mzl.SSOdemo1.interceptor;

import com.alibaba.fastjson.JSON;
import com.mzl.SSOdemo1.constant.UserConstant;
import com.mzl.SSOdemo1.result.ResultMap;
import com.mzl.SSOdemo1.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @ClassName :   AccessInterceptor
 * @Description: 允许通过处理的拦截器
 * @Author: mzl
 * @CreateDate: 2021/1/30 9:53
 * @Version: 1.0
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("每个请求前开始执行拦截器");
        if (handler instanceof HandlerMethod){
            //获取请求监听的控制器对象
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取请求方法上的目标注解
            InterceptFlag interceptFlag = handlerMethod.getMethodAnnotation(InterceptFlag.class);
            //接口上有目标注解，则进行拦截处理
            if (interceptFlag != null){
                //获取用户的请求token
                String requestToken = request.getHeader(UserConstant.USER_TOKEN);
                if (StringUtils.isEmpty(requestToken)){   //请求的token为空
                    render(response, new ResultMap(401, "请求参数非法，请求头不能为空", null));
                    return false;
                }
                //请求token不为空，则校验认证token
                String userId = authTokenService.checkToken(requestToken);
                if (StringUtils.isEmpty(userId)){  //发现用户的token为空（redis不存在，过期了 或 请求的token不对，与redis的不一致）
                    render(response, new ResultMap(402, "token错误（用户名错误），登录失败", null));
                    return false;
                }
            }
            //放行接口，允许执行
            return true;
        }

        return true;
    }

    /**
     * 转向输出请求token错误的原因及信息到respond的body区
     * @param response
     * @param resultMap
     * @throws Exception
     */
    private void render(HttpServletResponse response, ResultMap resultMap) throws Exception {
        System.out.println("转向输出请求token错误的原因及信息到respond的body区");
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(resultMap);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
