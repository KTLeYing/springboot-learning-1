package com.mzl.shirojwt.shiro.cache;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.mzl.shirojwt.constant.CommonConstant;
import com.mzl.shirojwt.shiro.jwt.JwtUtil;
import com.mzl.shirojwt.util.PropertiesUtil;
import com.mzl.shirojwt.util.RedisUtil;

/**
 * @ClassName :   UserCache
 * @Description: 重写用户的shiro的cache
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:34
 * @Version: 1.0
 */
public class UserCache<K, V> implements Cache<K, V> {

    /**
     * @Title: getKey @Description:
     * 缓存的key名称获取为shiro:cache:account)
     */
    private String getKey(Object key) {
        return CommonConstant.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), CommonConstant.ACCOUNT);
    }

    /**
     *获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        // 查看redis中是否存在该缓存
        if (!RedisUtil.hasKey(this.getKey(key))) {
            // 不存在。返回null
            return null;
        }
        // 存在则返回当前的缓存
        return RedisUtil.get(this.getKey(key));
    }

    /**
     *
     * Description:保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        PropertiesUtil.readProperties("application.properties");
        String refreshTokenExpireTime = PropertiesUtil.getProperty("shiroCacheExpireTime");
        // 设置Redis的Shiro缓存
        RedisUtil.setEx(key.toString(), value.toString(), Long.parseLong(refreshTokenExpireTime), TimeUnit.SECONDS);
        return refreshTokenExpireTime;//返回值没有意义
    }

    /**
     * Description:移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        // 查看redis中是否存在该缓存
        if (!RedisUtil.hasKey(this.getKey(key))) {
            // 不存在。返回null
            return null;
        }
        RedisUtil.delete(this.getKey(key));
        return null;
    }

    /**
     * Description:清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        RedisUtil.clearRedis();
    }

    /**
     * 设置大小
     */
    @Override
    public int size() {
        return RedisUtil.redisSize();
    }

    /**
     * Description:获取所有的key
     */
    @Override
    public Set keys() {
        return RedisUtil.keys();
    }

    /**
     * Description:获取所有的value
     */
    @Override
    public Collection values() {
        return RedisUtil.values();
    }

}
