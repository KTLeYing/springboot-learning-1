package com.mzl.jwtdemo1.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName :   JwtConfig
 * @Description: JWT配置类（JWT的token区分大小写的）
 * @Author: mzl
 * @CreateDate: 2020/10/9 12:25
 * @Version: 1.0
 */
@Data
@Component  //组件来的，注册到spring容器里面
@ConfigurationProperties(prefix = "config.jwt")  //用ConfigurationProperties来获取在.yml文件对应的配置，这里是前缀
public class JwtConfig {

    //这里可以不用@Value()注解，因为会和.yml或.properties文件同名匹配赋值（类似mybatis和controller接口参数的原理）
    private String secret;  //加密密匙
    private long expire;  //token过期的期限
    private String header;  //header名称

    /**
     * 生成token
     * @param subject
     * @return
     */
    public String createToken(String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);  //过期时间，秒要化成毫秒

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取token注册的信息，把token令牌解析来获取各种用户注册的信息(因为不能直接从token获取，token要转换为claim，再从从来没中获取自己需要的信息)
     * @param token
     * @return
     */
    public Claims getTokenClaim(String token){
        try{
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断token是否过期失效
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired(Date expirationTime){
        //如果期限在当前时间之前，则为未过期，返回true；否则为过期，返回false
        return expirationTime.before(new Date());
    }

    /**
     * 获取token的失效时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token){
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        return getTokenClaim(token).getSubject();  //subject就是主体/主人，也就是用户，即用户名（有些会用userId，能是用户的唯一标识就行）
    }

    /**
     * 从token中获取Jwt发布的时间
     * @param token
     * @return
     */
    public Date getIssueAtDateFromToken(String token){
        return getTokenClaim(token).getIssuedAt();
    }


}
