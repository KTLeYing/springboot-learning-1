package com.example.demo.dubbo.filter;

import com.example.demo.dubbo.utils.TraceIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 路径：com.example.demo.dubbo.filter
 * 类名：TraceLoggingFilter.java
 * 功能：请求时产生唯一的TraceID
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/13 18:28:28
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@Component
public class TraceLoggingFilter extends OncePerRequestFilter implements Ordered {

    private int order = Ordered.HIGHEST_PRECEDENCE;

    @Override
    public int getOrder() {
        return this.order;
    }

    /**
     * 方法名：
     * 功能：设置此过滤器的顺序
     * 描述：order： the order to set
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 方法名：doFilterInternal
     * 功能：拦截请求，生成traceID和跨域请求处理
     * 描述：
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            // 跨域请求处理
            String reqHead = request.getHeader("Origin");
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", reqHead);
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
            String method = request.getMethod();
            if (method.equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization,token");
                response.setStatus(200);
            }

            // 生成traceID
            if (TraceIDUtils.getTraceId() == null) {
                TraceIDUtils.setTraceId(UUID.randomUUID().toString());
                String mdcData = String.format("[TraceID:%s]", TraceIDUtils.getTraceId());
                MDC.put("mdcData", mdcData);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MDC.remove("mdcData");
        }
    }

}
