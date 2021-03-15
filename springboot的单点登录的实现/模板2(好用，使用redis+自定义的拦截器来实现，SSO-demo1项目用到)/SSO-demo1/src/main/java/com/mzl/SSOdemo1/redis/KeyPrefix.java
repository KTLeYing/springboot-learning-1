package com.mzl.SSOdemo1.redis;

/**
 * redis的key的配置接口——key的前缀
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
