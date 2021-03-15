package com.mzl.crossingdemo1.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   ProcessInterceptor
 * @Description: 使用HandlerInterceptor拦截器，与Filter类似
 * @Author: mzl
 * @CreateDate: 2020/11/28 15:02
 * @Version: 1.0
 */
@Component
public class ProcessInterceptor implements HandlerInterceptor {

  /**
   * 比如说：在Vue的项目里，Http服务采用Axios，而它正是采用OPTIONS请求。 如果仅仅在header里面加入：
   * ‘Access-Control-Allow-Origin’：*，是并不能解决问题的，错误就是如文章开头所示。 就需要我们在后台对OPTIONS请求额外处理，进行跨域问题的解决
   * @param request
   * @param response
   * @param handler
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("X-Powered-By","Jetty");
        String method= request.getMethod();
        if (method.equals("OPTIONS")){
            response.setStatus(200);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
