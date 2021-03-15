package com.mzl.SSOdemo1.redis;

/**
 * 用户的redis的主键的设置，继承通用的前缀
 */
public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    //创建用户可用的key（redis的token永远不过期，不设置有效期，不需要传expireSecond的参数，构造方法那边设置默认为0）
//    public static UserKey userAccessKey = new UserKey("access");

    //创建用户可用的key（redis的token设置有效期时间（单位秒），有效期为第一个参数expireSecond）
    public static UserKey userAccessKey = new UserKey(500, "access");
}
