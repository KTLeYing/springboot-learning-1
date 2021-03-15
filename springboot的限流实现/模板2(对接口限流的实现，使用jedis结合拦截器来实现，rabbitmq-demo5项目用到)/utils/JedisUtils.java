package com.mzl.rabbitmqdemo5.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.mzl.rabbitmqdemo5.config.JedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @ClassName :   JedisUtils
 * @Description: jedis工具类
 * @Author: mzl
 * @CreateDate: 2020/10/23 9:40
 * @Version: 1.0
 */
@Component
@Slf4j
public class JedisUtils {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key: {} value: {} error", key, value, e);
            e.printStackTrace();
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 设置值（有过期时间）
     * @param key
     * @param value
     * @param expireTime 过期时间, 单位: s
     * @return
     */
    public String set(String key, String value, int expireTime){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.setex(key,expireTime, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} expireTime:{} error", key, value, expireTime, e);
            e.printStackTrace();
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key, String value){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("set key: {} value: {} error", key, value, e);
            e.printStackTrace();
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 取值
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key, e);
            e.printStackTrace();
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public Long del(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("del key:{} error", key, e);
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean exist(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            log.error("exists key:{} error", key, e);
            e.printStackTrace();
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 设置key的过期时间
     * @param key
     * @return
     */
    public Long expire(String key, int expireTime){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.expire(key.getBytes(), expireTime);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("expire key:{} error", key, e);
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 获取剩余时间
     * @param key
     * @return
     */
    public Long ttl(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            return jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ttl key:{} error", key, e);
            return null;
        }finally{
            close(jedis);
        }
    }

    /**
     * 关闭jedis
     * @param jedis
     */
    public void close(Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }


}
