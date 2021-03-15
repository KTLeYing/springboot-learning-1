package com.mzl.SSOdemo1.redis;


/**
 * redis的通用的key的具体设置接口的实现类
 */
public class BasePrefix implements KeyPrefix {

    //过期的标志（0代表永不过期）
    private int expireSeconds;
    //key的前缀
    private String prefix;

    public BasePrefix(String prefix) {//0代表永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
