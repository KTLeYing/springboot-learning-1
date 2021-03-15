package com.mzl.securitydemo4.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName :   CustomAuthenticationDetailsSource
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/13 21:57
 * @Version: 1.0
 */
@Component("authenticationDetailsSource")
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    /**
     * 自定义了WebAuthenticationDetails，我i们还需要将其放入 AuthenticationDetailsSource 中来替换原本的 WebAuthenticationDetails ，
     * 因此还得实现自定义 AuthenticationDetailsSource ：
     * 该类内容将原本的 WebAuthenticationDetails 替换为了我们的 CustomWebAuthenticationDetails
     * @param request
     * @return
     */

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new CustomWebAuthenticationDetails(request);   //WebAuthenticationDetails 替换为了我们的 CustomWebAuthenticationDetails
    }

  /**
   * 然后我们将 CustomAuthenticationDetailsSource 注入Spring Security中，替换掉默认的 AuthenticationDetailsSource。
   *修改 WebSecurityConfig，将其注入，然后在config()中使用
   * authenticationDetailsSource(authenticationDetailsSource)方法来指定它。
   */
}
