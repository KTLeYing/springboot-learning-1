package com.mzl.securitydemo4.security;

import lombok.Data;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName :获取用户登录时携带的额外信息
 * @Author: mzl
 * @CreateDate: 2020/11/13 21:46
 * @Version: 1.0
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    /**
     * 我们知道 Spring security 默认只会处理用户名和密码信息。这时候就要请出我们的主角——WebAuthenticationDetails。
     WebAuthenticationDetails: 该类提供了获取用户登录时携带的额外信息的功能，默认提供了 remoteAddress 与 sessionId 信息。
     我们需要实现自定义的 WebAuthenticationDetails，并在其中加入我们的验证码：
     */

    private static final long serialVersionUID = 6975601077710753878L;
    private String verifyCode = null;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // verifyCode为页面中验证码的name
        verifyCode = request.getParameter("verifyCode");
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append(super.toString()).append("; VerifyCode: ").append(this.getVerifyCode());
       return sb.toString();
    }
}
