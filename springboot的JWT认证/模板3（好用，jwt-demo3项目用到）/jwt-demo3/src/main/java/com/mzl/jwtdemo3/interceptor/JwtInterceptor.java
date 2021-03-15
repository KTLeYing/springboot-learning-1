package com.mzl.jwtdemo3.interceptor;

import com.alibaba.fastjson.JSON;
import com.mzl.jwtdemo3.constant.CommonConstant;
import com.mzl.jwtdemo3.result.ModelResult;
import com.mzl.jwtdemo3.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName :   JwtInterceptor
 * @Description: Jwt拦截器
 * @Author: mzl
 * @CreateDate: 2020/10/10 15:40
 * @Version: 1.0
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    public static final String USER_INFO_KEY = "username";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户token
        String token = request.getHeader(JwtUtils.getHeaderKey());

        //判断token是否为空
        if (StringUtils.isEmpty(token)){
            this.writeErrorMsg(CommonConstant.UNAUTHORIZED, JwtUtils.getHeaderKey() + "不能为空登录", response);
            return false;
        }

        //校验并解析token，如果token过期或者篡改(不正确)，则会返回null
        Claims claims = JwtUtils.verifyAndGetClaimsByToken(token);
        if (claims == null){
            this.writeErrorMsg(CommonConstant.UNAUTHORIZED, JwtUtils.getHeaderKey() + "失效，请重新登录", response);
            return false;
        }

        //  校验通过后，设置用户信息到request里，在Controller中从Request域中获取用户信息
        request.setAttribute(USER_INFO_KEY, claims);
        return true;
    }

    /**
     * 利用response直接输出错误信息
     * @param code
     * @param msg
     * @param response
     * @throws IOException
     */
    public void writeErrorMsg(String code, String msg, HttpServletResponse response) throws IOException {
        ModelResult<String> result = new ModelResult<>();
        result.setCode(code);
        result.setMsg(msg);

        //用response对象输入到前端的body区，相当于@ResponBody注解
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
