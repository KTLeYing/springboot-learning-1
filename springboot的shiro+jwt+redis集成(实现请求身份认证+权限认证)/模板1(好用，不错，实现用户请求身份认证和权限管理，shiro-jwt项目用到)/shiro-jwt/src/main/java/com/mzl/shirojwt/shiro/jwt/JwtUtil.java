package com.mzl.shirojwt.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mzl.shirojwt.constant.CommonConstant;
import com.mzl.shirojwt.exception.MyException;
import com.mzl.shirojwt.util.Base64ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @ClassName :   JwtUtil
 * @Description: jwt工具类
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:28
 * @Version: 1.0
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 过期时间
     */
    private static String accessTokenExpireTime;
    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey;

    /**
     * 解决@Value不能修饰static的问题
     */
    @Value("${accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Value("${encryptJWTKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtUtil.encryptJWTKey = encryptJWTKey;
    }

    /**
     * 检验token是否有效
     */
    public static boolean verify(String token) {
        try {
            // 通过token获取密码
            String secret = getClaim(token, CommonConstant.ACCOUNT) + Base64ConvertUtil.encode(encryptJWTKey);
            // 进行二次加密
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 使用JWTVerifier进行验证解密
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            logger.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
            return false;
        }
    }

    /**
     *
     * @Title: sign @Description: 生成签名
     */
    public static String sign(String account, String currentTimeMillis) {
        try {
            // 使用私钥进行加密
            String secret = account + Base64ConvertUtil.encode(encryptJWTKey);
            // 设置过期时间(配置文件设置过期时间是5分钟)：根据当前时间计算出过期时间。 此处过期时间是以毫秒为单位，所以乘以1000。
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            // 对私钥进行再次加密
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 生成token 附带account信息
            String token = JWT.create().withClaim("account", account).withClaim("currentTimeMillis", currentTimeMillis)
                    .withExpiresAt(date).sign(algorithm);
            System.out.println("用户登录成功后生成的token为：" + token);
            return token;
        } catch (UnsupportedEncodingException e) {
            logger.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new MyException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }

    }

    /**
     *
     * @Title: getClaim @Description:
     * 获取token中的信息就是withClaim中设置的值)
     * @param:
     * @param token
     * @param:
     * @param claim:sign()方法中withClaim设置的值 @param: @return @return: String @throws
     */
    public static String getClaim(String token, String claim) {
        try {
            // 对token进行解码获得解码后的jwt
            DecodedJWT jwt = JWT.decode(token);
            // 获取到指定的claim,如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            logger.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            throw new MyException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }
}
