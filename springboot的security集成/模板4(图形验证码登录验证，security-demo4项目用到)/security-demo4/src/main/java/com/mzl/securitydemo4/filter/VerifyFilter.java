package com.mzl.securitydemo4.filter;

import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName :   VerifyFilter
 * @Description: 验证码验证过滤器
 * @Author: mzl
 * @CreateDate: 2020/11/13 21:00
 * @Version: 1.0
 */
public class VerifyFilter extends OncePerRequestFilter {

  /**
   * 自定义一个过滤器，实现 OncePerRequestFilter （该 Filter 保证每次请求一定会过滤），在 isProtectedUrl() 方法中拦截了 POST 方式的
   * /login 请求。
   * <p>在逻辑处理中从 request 中取出验证码，并进行验证，如果验证成功，放行；验证失败，手动生成异常
   */
  private static final PathMatcher pathMatcher = (PathMatcher) new AntPathMatcher();

    /**
     * 过滤器
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (isProtectedUrl(httpServletRequest)){
            String verifyCode = httpServletRequest.getParameter("verifyCode");
            if (!validateVerify(verifyCode)){
                //手动设置异常，自定义security异常，属性是security自带的SPRING_SECURITY_LAST_EXCEPTION
                httpServletRequest.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new DisabledException("输入验证码错误"));
                // 转发到错误Url页面
                httpServletRequest.getRequestDispatcher("/loginError").forward(httpServletRequest, httpServletResponse);
            }else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);  //放行该请求
            }
        }else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);  //放行该请求
        }
    }

    /**
     * 用户输入的验证码和图片的真实验证码比较是否一致
     * @param inputVerify
     * @return
     */
    private boolean validateVerify(String inputVerify){
        //获取当前线程绑定的request对象
        ServletRequestAttributes attributes;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个validateCode是在servlet中存入session的名字
        String validateCode = (String) request.getSession().getAttribute("validateCode");

        System.out.println("验证码：" + validateCode + "用户输入：" + inputVerify);
        return validateCode.equalsIgnoreCase(inputVerify);
    }

    /**
     * 拦截要处理的目标路径，/login的POST请求,判断是否是目标路径，不是登录请求则不拦截，只处理登录验证码请求验证路径
     * @return
     */
    private boolean isProtectedUrl(HttpServletRequest request){
        return "POST".equals(request.getMethod()) && pathMatcher.match("/login", request.getServletPath());
    }
}
