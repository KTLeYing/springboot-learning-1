//package com.mzl.crossingdemo1.config;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @ClassName :   CorsFilter
// * @Description: 后端跨域处理方法2：跨域过滤器，对跨域请求进行过滤处理放行，手工设置响应头（HttpServletResponse ）,直接在spring容器里作为原料并使用原料起作用了
// * @Author: mzl
// * @CreateDate: 2020/10/26 1:47
// * @Version: 1.0
// */
//@Component
//public class MyCorsFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        //*号表示对所有请求都允许跨域访问,*在哪里都代表所有的意思
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.addHeader("Access-Control-Allow-Credentials", "true");
////        response.addHeader("Access-Control-Allow-Origin", "*");  //全局跨域，所有
//        response.addHeader("Access-Control-Allow-Origin", "https://www.baidu.com");  //局部跨域
////        response.addHeader("Access-Control-Allow-Methods", "*");  //所有请求
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//        response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
//        if (((HttpServletRequest)servletRequest).getMethod().equals("OPTIONS")){
//            response.getWriter().println("SUCCESS");
//            return;
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
