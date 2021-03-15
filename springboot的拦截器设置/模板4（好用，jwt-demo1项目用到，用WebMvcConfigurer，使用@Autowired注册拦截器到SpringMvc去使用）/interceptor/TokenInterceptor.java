package com.mzl.jwtdemo1.interceptor;

import com.mzl.jwtdemo1.config.JwtConfig;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;

/**
 * @ClassName :   TokenInterceptor
 * @Description: token登录拦截器
 * @Author: mzl
 * @CreateDate: 2020/10/9 15:35
 * @Version: 1.0
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtConfig jwtConfig;  //注入依赖（使用注册到容器里面的原料）

    /**
     *  执行controller接口前执行，进行请求拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截开始...");
        //进行地址过滤
        String uri = request.getRequestURI();
        System.out.println("uri:" + uri);
        if (uri.contains("/login")){
            //如果是去请求登录的路径，就放行，否则是其他路径的话，一律拦截下来然后再做下一轮的判断处理
            return true;
        }

        //开始对每个拦截的请求进行token验证
        String token = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.isEmpty(token)){
            token = request.getParameter(jwtConfig.getHeader());
            System.out.println("token:" + token);
        }

        if (StringUtils.isEmpty(token)){
            throw new SignatureException(jwtConfig.getHeader() + "不能为空");
        }

        Claims claims = null;
        try{
            claims = jwtConfig.getTokenClaim(token);
            System.out.println("claims:" + claims);
            if (claims == null || jwtConfig.isTokenExpired(claims.getExpiration())){
                throw new SignatureException(jwtConfig.getHeader() + "失效，请重新登录。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SignatureException(jwtConfig.getHeader() + "失效，请重新登录。");
        }

        //设置用户身份id，使用用户名(主体）
        request.setAttribute("identityId", claims.getSubject());
        System.out.println("identityId:" + request.getAttribute("identityId"));

        return true;
    }

    /**
     * 拦截结束后执行，即在controller之后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截结束...");
    }

}
