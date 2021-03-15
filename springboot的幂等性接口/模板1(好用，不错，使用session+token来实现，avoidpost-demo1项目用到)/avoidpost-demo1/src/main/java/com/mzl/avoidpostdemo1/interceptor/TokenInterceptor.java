package com.mzl.avoidpostdemo1.interceptor;

import com.mzl.avoidpostdemo1.annotation.Token;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @ClassName :   TokenController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/22 15:58
 * @Version: 1.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    //The Constant TOKEN. 放在session中的token
    private static final String TOKEN = "token";

    /**
     * 执行接口前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            //获取控制器的方法
            Method method = ((HandlerMethod) handler).getMethod();  //获取控制器的方法
            Token tokenAnnotation = method.getAnnotation(Token.class);  //获取方法上的注解
            System.out.println("判断方法是否有自定义注解");
            if (tokenAnnotation != null){  //注解不为空，则判断是否放行；注解为空则直接发行
                HttpSession session = request.getSession();

                // 创建新的表单提交令牌token,防止表单重复提交
                boolean isGenerate = tokenAnnotation.generate();  //获取自定义注解token里的属性
                if (isGenerate){
                    String formToken = UUID.randomUUID().toString();  //产生一个token
                    session.setAttribute(TOKEN, formToken);   //设置toke会话，前端页面会获取
                    System.out.println("创建表单提交令牌成功,token为：" + formToken);
                    return true;
                }

                // 第一次提交订单成功后删除token令牌
                boolean remove = tokenAnnotation.remove();
                System.out.println("yyy");
                if (remove){
                    if (isRepeatSubmit(request)){   //如果返回为真，则表明为重复提交订单或者是token参数的错误
                        System.out.println("表单不能重复提交: " + request.getRequestURI());
                        return false;   //拦截请求，重复提交了
                    }
                    System.out.println("表单成功提交: " + request.getRequestURI());
                    session.removeAttribute(TOKEN);
                }
            }
        }else {
            return super.preHandle(request, response, handler);
        }

        return true;
    }

    /**
     * 判断是重复提交表单
     * @param request
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request) {
        //session中token
        String token = (String) request.getSession().getAttribute(TOKEN);
        if (StringUtils.isEmpty(token)){   //如果服务器中token的session为空，则代表是重复提交
            System.out.println("ooo");
            return true;   //返回true，代表是重复提交
        }

        //请求头中获取token（token可能放在请求中也可能放在请求参数里面）
        String reqToken = request.getHeader(TOKEN);
        if (StringUtils.isEmpty(reqToken)){
            System.out.println("jjj");
            //从请求参数里获取token
            reqToken = request.getParameter(TOKEN);
            if (StringUtils.isEmpty(reqToken)){
                System.out.println("kkk");
                return true;
            }
        }

        //判断session中的token是否等于参数或者请求头的session(参数token错误)
        if (!token.equals(reqToken)){
            System.out.println("iii");
            return true;
        }

        System.out.println("mmm");
        return false;
    }


    /**
     * 接口执行完成后执行（返回值后）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器放行了该请求！！！");
    }


}
