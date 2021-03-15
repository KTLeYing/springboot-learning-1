package com.mzl.csrfdemo4.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName :   MyInterceptor
 * @Description: 自定义拦截器
 * @Author: mzl
 * @CreateDate: 2021/3/11 21:55
 * @Version: 1.0
 */
@Component
@Slf4j
//@EnableConfigurationProperties   //可以注入属于配置属性的依赖
public class CsrfInterceptor implements HandlerInterceptor {

    @Value("${csrf.releaseDomainList}")
    private List<String> domains;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String refer = request.getHeader("Referer");
        //如果请求头的refer参数为空，则拦截
        if (StringUtils.isEmpty(refer)){
            return false;
        }
        //遍历域名
        for (String domain : domains){
            if (refer.trim().equals(domain)){
                return true;
            }
        }

        return false;
    }
    
}
