package com.mzl.shirojwt.shiro.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mzl.shirojwt.config.Config;
import com.mzl.shirojwt.constant.CommonConstant;
import com.mzl.shirojwt.shiro.jwt.JwtToken;
import com.mzl.shirojwt.shiro.jwt.JwtUtil;
import com.mzl.shirojwt.util.PropertiesUtil;
import com.mzl.shirojwt.util.RedisUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   JwtFilter
 * @Description: jwt的过滤器,继承了shiro的Web的1http的认证过滤器
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:28
 * @Version: 1.0
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    /**
     * 对跨域的支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("执行接口过滤验证前执行，进行跨域的配置支持");
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", httpServletRequest.getHeader("GET,POST,DELETE,PUT,OPTIONS"));
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     *这里是对http请求进行处理(看是否允许访问)
     *  这里我们详细说明下为什么最终返回的都是true，即允许访问 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     *
     * 核心原理过程：当用户请求的token的时间（5分钟）还没过期，执行请求认证(executeLogin)是不会抛出异常的，不需要处理redis的token，直接认证成功了；当用户
     * 请求的token过期无效了，执行请求认证(executeLogin)抛出异常，开始去处理异常，如果发现的token的过期异常，就进程异常处理，发现过期后的首次redis的时间戳
     * 是等于token的时间戳的（key的值），就进行处置token，主要是处置token的时间戳，更新为当前时间戳，包括redis的token的时间戳和用户请求的token(更新后返回给
     * 前端的用户的header里面，下次就需要用新的header来请求了，不然还是拿以前的用户token来请求的话，那么redis开始进行倒计时，用户的请求是否有效就只能有
     * redis的token的有效期去决定了，因为用以前的请求的token时间戳是不会和redis的token的时间戳一样的，所以是不会相等的，所以redis就不会更新了，而用户的
     * token也过期了（用原来的那个token话)。如果那新的token，过期后更新后的，用户请求token不会过期了，然后redis的token的时间戳（值）也得到更新了
     * 另外，如果使用以前的用户token来请求的话，即使过滤器的初始的简单认证通过了，但是进入到shiro用户认证也不能通过，因为请求的token过期了，shiro会抛出异
     * 常返回false，不能成功通过认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("进行请求进行处理，看是否允许访问...");
        //先对当前的url判断是否放行
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        System.out.println("当前请求的URI为：" + requestURI);
        if (ReleaseAddressUtil.confirm(requestURI)){  //对不需要认证的接口进行放行
            System.out.println("请求 " + requestURI + " 被放行了...");
            return true;
        }
        System.out.println("请求 " + requestURI + " 被拦截了，下面开始进行认证...");

        if (isLoginAttempt(request, response)){  //有token,则去认证
            //进行shiro的登录userRealm
            try{
                this.executeLogin(request, response);   //执行请求的认证
                System.out.println("请求认证成功，没有抛出异常，不需要处理redis的token");
            } catch (Exception e) {   //对请求认证异常进行处理
                // 认证出现异常，传递错误信息msg
                String msg = e.getMessage();
                // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
                Throwable throwable = e.getCause();
                if (throwable instanceof SignatureException){
                    // 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                    System.out.println(msg);
                }else if (throwable instanceof TokenExpiredException){
                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    // 刷新token
                    if (refreshToken(request, response)){
                        //刷新token成功，允许访问
                        System.out.println("请求认证成功，刷新redis的token成功...");
                        return  true;
                    }else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
                        System.out.println(msg);
                    }
                    System.out.println("请求认证成功...");
                    return true;
                }else {  //否则其他的认证异常
                    // 应用异常不为空
                    if (throwable != null) {
                        // 获取应用异常msg
                        msg = throwable.getMessage();
                        System.out.println(msg);
                    }
                }

                System.out.println("请求认证失败，不能访问...");
                this.response401(request, response, msg);
                //不允许访问，登录认证失败
                return  false;
            }
        }else {   //没有token
            // 没有携带Token
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            // 获取当前请求类型
            String httpMethod = httpServletRequest.getMethod();
            //获取当前请求的URI
            System.out.println("当前请求 " + requestURI + " Authorization属性(Token)为空，请求类型为 " + httpMethod);
            logger.info("当前请求 {} Authorization属性(Token)为空，请求类型为 {}", requestURI, httpMethod);
//             mustLoginFlag = true //开启任何请求必须登录才可访问
            if (!Config.mustLoginFlag){  //是否开发游客权限
                //为false，表明没开启游客权限，一定要先登录才能访问所有的接口
                this.response401(httpServletRequest, response, "请先登录");
                return false;
            }
        }
        return true;
    }

    /**
     * 检测header里是否含有Authorization字段。
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        System.out.println("进行登录尝试，检测header里是否含有Authorization字段...");
        //返回header中Authorization对应的token的值
        String token = this.getAuthzHeader(request);
        return token != null;    //如果token不为空，则返回true
    }

    /**
     *  进行AccessToken登录认证授权
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("进行AccessToken登录认证授权...");
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        JwtToken token = new JwtToken(this.getAuthzHeader(request));
        // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
        this.getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * (刷新token,具体原理：先查看redis是否存在该key。如果存在取出时间。然后与AccessToken中的时间进行比对。相同则进行更新redis
     * 中key的失效时间。提交给shiro进行再次登录，将新生成的token写入到response中返回)
     * @param request
     * @param response
     * @return
     */
    protected boolean refreshToken(ServletRequest request, ServletResponse response){
        System.out.println("进行Token刷新操作...");
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取当前Token的帐号信息
        String account = JwtUtil.getClaim(token, CommonConstant.ACCOUNT);
        System.out.println("请求的token的账号为：" + account);
        // 判断Redis中RefreshToken是否存在
        if (RedisUtil.hasKey(CommonConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)){  //存在刷新的用户token
            // Redis中RefreshToken还存在，获取RefreshToken的时间戳(时间戳是redis的值来的)
            String currentTimeMillisRedis = RedisUtil.get(CommonConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
            System.out.println("redis在当前的token的时间戳（redis的值）：" + currentTimeMillisRedis);
            // 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
            System.out.println("当前请求的token中的时间戳（用于判断是否和redis的一样）：" + JwtUtil.getClaim(token, CommonConstant.CURRENT_TIME_MILLIS));
            if (JwtUtil.getClaim(token, CommonConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)){
                System.out.println("redis在当前的token的时间戳（redis的值） == 当前请求的token中的时间戳（用于判断是否和redis的一样）");
                // 获取当前最新时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                System.out.println("当前系统的时间戳：" + currentTimeMillis);
                // 读取配置文件，获取refreshTokenExpireTime属性
                PropertiesUtil.readProperties("application.properties");
                String refreshTokenExpireTime = PropertiesUtil.getProperty("refreshTokenExpireTime");
               //设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
                RedisUtil.setEx(CommonConstant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis,
                        Long.parseLong(refreshTokenExpireTime), TimeUnit.SECONDS);
                // 刷新AccessToken延长过期时间，设置时间戳为当前最新时间戳，重新获取新的token（请求一次就刷新重置一次时间戳）
                System.out.println("重新设置了token的时间戳...");
                token = JwtUtil.sign(account, currentTimeMillis);
                // 将新刷新的AccessToken再次进行Shiro的登录
                JwtToken jwtToken = new JwtToken(token);
                //提交给userRealm进行认证,如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
                //设置用户的主题给shiro，用于以后的用户登录的自动校验登录
                this.getSubject(request, response).login(jwtToken);
                // 将token刷入response的header中
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setHeader("Authorization", token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                //登录成功，返回true
                return true;
            }
        }
        //否则登录认证失败
        return false;
    }


    /**
     *   缺少权限内部转发至401处理
     */
    private void response401(ServletRequest request, ServletResponse response, String msg){
        System.out.println("执行response401处理...");
        HttpServletRequest req = (HttpServletRequest) request;
        try{
            req.getRequestDispatcher("/user/401").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
