package com.mzl.jwtdemo2.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @ClassName :   JwtUtil
 * @Description: Jwt配置工具类
 * @Author: mzl
 * @CreateDate: 2020/10/9 21:00
 * @Version: 1.0
 */
public class JwtUtil {

    //设置过期时间，过期时间为一个星期
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;  //以毫秒懂的格式
    //签名加密密匙
    private static final String SECRET = "xipiker";

    /**
     * 生成一个token
     * @param userCode
     * @return
     */
    public static String createJWT(String userCode){
        try{
            //时间戳转换为时间日期
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //用加密密匙加密，相对于加盐
            Algorithm algorithm = Algorithm.HMAC512(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("userCode", userCode)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token
     * @param token
     * @param userCode
     * @return
     */
    public static boolean verify(String token, String userCode){
        try{
            Algorithm algorithm = Algorithm.HMAC512(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim("userCode", userCode).build();
            jwtVerifier.verify(token);
            return true;   //用JWTVerifier来代替自定义的拦截器来验证token，方便
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token
     * @return
     */
    public static String getUserCode(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);  //用DecodedJWT来解密获取
            return jwt.getClaim("userCode").asString();  //返回用户的信息
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
