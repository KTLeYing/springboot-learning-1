package com.mzl.shirojwtSSO.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName :   JwtToken
 * @Description: 对token进行扩展,实现重写shiro里的AuthenticationToken的token的属性和方法
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:47
 * @Version: 1.0
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
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
