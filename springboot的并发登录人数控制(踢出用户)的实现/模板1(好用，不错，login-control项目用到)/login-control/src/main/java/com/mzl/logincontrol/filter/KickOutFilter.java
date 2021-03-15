package com.mzl.logincontrol.filter;

import com.mzl.logincontrol.pojo.CurrentUser;
import com.mzl.logincontrol.pojo.UserBO;
import com.mzl.logincontrol.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class KickOutFilter implements Filter {

    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RedissonClient redissonClient;

    @Autowired
    public UserService userService;

    public static final String PREFIX = "uni_token_";

    public static final String PREFIX_LOCK = "uni_token_lock_";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("执行过滤器");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            //请求的token合法且允许访问则放行请求
            if (checkToken(request, response) && isAccessAllowed(request, response)) {
                filterChain.doFilter(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 当前用户是否允许访问
     *
     * @param request
     * @param response
     * @return
     */
    public abstract boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 检查是否携带token 以及token是否失效
     *
     * @param request
     * @param response
     * @return
     */
    public boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {  //没有携带token来请求（比如说登录请求）
            sendJsonResponse(response, 401, "你无权访问");
            return false;
        }

        // 校验token是否存在
        //类似redis的操作，获取redission的token（key）对应的用户信息值
        RBucket<UserBO> rBucket = redissonClient.getBucket(token);
        //从token中解析出用户信息
        UserBO userBO = rBucket.get();

        if (userBO == null) {   //从token找不到用户，证明该token是错误的或者redis的用户令牌过期了，需要重新登录
            sendJsonResponse(response, 403, "无效令牌");
            return false;
        }

        //设置当前的用户线程
        CurrentUser.put(userBO);
        return true;
    }

    /**
     * 发送token处理
     * @param resp
     * @param code
     * @param message
     */
    public static void sendJsonResponse(HttpServletResponse resp, int code, String message) {
        sendJsonResponse(resp, String.format(jsonTemplate(), code, message));
    }

    /**
     * json格式模板
     * @return
     */
    public static String jsonTemplate() {
        return "{\"code\":%s,\"msg\":\"%s\",\"data\":null,\"errors\":null}";
    }

    /**
     * 真正处理发送json响应
     * @param response
     * @param json
     */
    public static void sendJsonResponse(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
