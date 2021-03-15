package com.mzl.shirojwt.shiro.jwt;

import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName :   JwtToken
 * @Description: Jwt的token用户身份验证封装，实现了shiro的认证token类
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:28
 * @Version: 1.0
 */
@NoArgsConstructor
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
