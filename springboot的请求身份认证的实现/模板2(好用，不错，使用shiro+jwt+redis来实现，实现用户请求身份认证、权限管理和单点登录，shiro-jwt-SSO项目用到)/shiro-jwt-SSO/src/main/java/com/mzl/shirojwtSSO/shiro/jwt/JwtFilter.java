package com.mzl.shirojwtSSO.shiro.jwt;

import com.mzl.shirojwtSSO.exception.MyException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * @ClassName :   JwtFilter
 * @Description: jwt过滤器，对用户请求的token进行处理
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:45
 * @Version: 1.0
 */
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 如果带有 token，则对 token 进行检查，否则直接通过
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        System.out.println("执行了是否允许访问的处理...");
        //判断请求的请求头是否带上 "token"
        if (isLoginAttempt(request, response)){
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try{
                executeLogin(request, response);
                System.out.println("请求的用户身份认证成功了...");
                return true;
            } catch (Exception e) {
                //token 错误
                responseError(request, response, e.getMessage());
                e.printStackTrace();
            }
        }

        //请求如果不携带token，则请求失败
        System.out.println("请求的用户身份认证失败了...");
        responseError(request, response, "请求没携带token，请求token不能为空，认证失败!");
        return false;
    }

    /**
     * 判断用户是否想要登录，是否要尝试登录
     * 检测 header 里面是否包含 token 字段，有token说明用户要请求，所以就进行用户token的验证；无token说明用户要进行登录或者游客状态访问
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        System.out.println("执行了判断用户是否想要登录...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        return token != null;
    }

    /**
     * 执行登录操作，看用户的token信息是否正确和用户是否有权限
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("执行了登录操作...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入验证，如果错误它会抛出异常并被捕获（realm的身份认证和权限管理通过才能算是放行true，否则会抛出异常）
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 执行接口各项处理前的执行，对跨域的支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("执行了接口各项处理前的执行...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /user/unauthorized/**
     */
    private void responseError(ServletRequest request, ServletResponse response, String message) {
        System.out.println("执行response401处理（身份认证失败或无权限）...");
        HttpServletRequest req = (HttpServletRequest) request;
        try{
            req.getRequestDispatcher("/user/unauthorized/" + message).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
