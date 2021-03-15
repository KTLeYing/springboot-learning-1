package com.mzl.shirojwtSSO.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @ClassName :   JwtUtil
 * @Description: jwt工具类
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:44
 * @Version: 1.0
 */
public class JwtUtil {

    // 过期时间 24 小时
    private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;
    // 密钥
    private static final String SECRET = "chen";

    /**
     * 生成token
     * @param username
     * @return
     */
    public static String createToken(String username){
        try{
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //对用户信息进行加密
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)  //用户的唯一标志（用户名）
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 校验 token 是否正确
     */
    public static boolean verify(String token, String username) {
        System.out.println("执行了校验 token...");
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息,使用当前用户名和对应算法创建一个用户验证器来验证token是否正确，因为和之前生成token的算法都是一样的
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            //验证 token 是否正确
            verifier.verify(token);
            System.out.println("校验 token 成功...");
            return true;
        } catch (Exception exception) {
            System.out.println("校验 token 失败...");
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getUsername(String token) {
        try {
            //解析token中用户的信息
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
