package com.mzl.shirojwt.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @ClassName : UserCacheManager
 * @Description: 重写shiro的的cache缓存管理器
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:35
 * @Version: 1.0
 */
public class UserCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new UserCache<K, V>();
    }
}
