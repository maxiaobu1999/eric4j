package com.eric.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

/**
 * redis 缓存管理
 */
public class RedisCacheManager implements CacheManager {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(s, redisUtils);
    }
}
