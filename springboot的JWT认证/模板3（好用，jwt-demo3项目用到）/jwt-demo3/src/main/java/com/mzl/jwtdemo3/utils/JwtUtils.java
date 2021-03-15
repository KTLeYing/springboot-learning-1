package com.mzl.jwtdemo3.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName :   JwtUtils
 * @Description: jwt配置工具类
 * @Author: mzl
 * @CreateDate: 2020/10/9 22:13
 * @Version: 1.0
 */
@Slf4j
public class JwtUtils {

    //请求头名称
    public static final String headerKey = "token";
    //加密的密匙secret
    public static final String secret = "MZLaiGQY1314";
    //过期时间，单位为秒
    public static final long expire = 1800L;

    //静态变量初始化器
    static {
        //上面的变量从初始化器中读取，为了方便测试，就不这里从配置文件中读取了
        //利用配置文件中的值来覆盖静态变量初始化的值
    }

    /**
     *生成一个token,所以通俗的来讲，token = base64（header） + "." + base64(payload) + "." + 签名
     * @param userInfoMap
     * @return
     */
    public static String generateToken(Map<String, Object> userInfoMap){
        if (Objects.isNull(userInfoMap)){
            userInfoMap = new HashMap<>();
        }
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")  //设置头部
                .setExpiration(expireDate)   //设置token的有效期
                .setClaims(userInfoMap)   //装入用户的信息
                .signWith(SignatureAlgorithm.HS512, secret)  //用算法对签名密匙进一步加密
                .compact();
    }

    /**
     * 校验并解析token，解析在claims才能获取
     * @param token
     * @return
     */
    public static Claims verifyAndGetClaimsByToken(String token){
        try{
            /* 如果过期或者是被篡改，则会抛异常.
                注意点：只有在生成token设置了过期时间(setExpiration(expireDate))才会校验是否过期，
                可以参考源码io.jsonwebtoken.impl.DefaultJwtParser的299行。
                拓展：利用不设置过期时间就不校验token是否过期的这一特性，我们不设置Expiration;
                      而采用自定义的字段来存放过期时间放在Claims（可以简单的理解为map）中;
                      通过token获取到Claims后自己写代码校验是否过期。
                      通过这思路，可以去实现对过期token的手动刷新
            */
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取请求头名称
     * @return
     */
    public static String getHeaderKey(){
        return headerKey;
    }

}
