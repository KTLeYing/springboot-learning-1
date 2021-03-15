package com.mzl.shirojwt.constant;

/**
 * @ClassName :   CommonConstant
 * @Description: 通用的常量
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:24
 * @Version: 1.0
 */
public class CommonConstant {

    /**
     * redis-OK设置成功
     */
    public final static String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public final static int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public final static int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache（缓存的）
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token（用户token的）
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token（刷新的token的）
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-account（jwt的1用户）:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis当前时间:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN（密码的最大的长度）
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

}
