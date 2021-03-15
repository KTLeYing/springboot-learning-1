package com.mzl.SSOdemo2.filter;

import com.alibaba.druid.util.StringUtils;
import com.mzl.SSOdemo2.util.CookieUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.invoke.MethodHandle;

/**
 * @ClassName :   LoginFilter
 * @Description: 单点登录过滤器
 * @Author: mzl
 * @CreateDate: 2021/2/8 22:38
 * @Version: 1.0
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "loginFilter")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter初始化中...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("开始进行过滤处理../");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //获取用户客户端cookie
        String cookieName = "TOKEN";
        String cookies = CookieUtils.getCookieValue(request, cookieName);
        if (!StringUtils.isEmpty(cookies)){
            System.out.println("请求放行了...");
            System.out.println("cookies还存在，不用重新登录，单点登录成功！cookies的值:" + cookies);
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            request.getRequestDispatcher("/user/index").forward(servletRequest, servletResponse);
            System.out.println("请求拦截了...");
        }
    }

    @Override
    public void destroy() {
        System.out.println("Filter销毁中...");
    }
}
