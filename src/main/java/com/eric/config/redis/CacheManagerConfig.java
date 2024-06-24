package com.eric.config.redis;

import com.eric.properties.CacheConfigProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.Duration;


@Configuration
@EnableCaching
public class CacheManagerConfig {
    @Resource(name = "jsonRedisTemplate")
    RedisTemplate redisTemplate;
    @Resource
    private CacheConfigProperties cacheConfigProperties;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        //设置默认超过期时间是1天
        defaultCacheConfig.entryTtl(Duration.ofDays(1));
        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);



        return cacheManager;
    }

//    @Bean
//    public CacheManager cacheManagerCustomizer() {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        cacheManager.setDefaultExpiration(3600);//默认缓存一小时
//        cacheManager.setExpires(cacheConfigProperties.getExpires());
//        return cacheManager;
//    }

}
