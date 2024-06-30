package com.eric.redis;

import com.eric.utils.DigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * token管理
 */
@Component
public class TokenManager {
    private static final Logger logger = LoggerFactory.getLogger(TokenManager.class);
    @Resource(name = "jsonRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;
    private static RedisTemplate<String, String> staticRedisTemplate;

    @PostConstruct
    public void init() {
        staticRedisTemplate = redisTemplate;
    }

    /**
     * 生成token
     *
     * @param userId 用户id
     * @return token
     */
    // @CachePut:指定key，将更新的结果同步到redis中。值前面会有\xac\xed\x00\x05t\x00，不使用
//    @CachePut(value = "token", key = "T(com.norman.common.TokenManager).generateTokenCacheKey(#userId)")
    public String generateToken(long userId) {
        String src = String.valueOf(userId) + System.currentTimeMillis();
        String token = DigestUtil.md5(src);
        logger.info("生成token，userId:{}，token:{}", userId, token);
        redisTemplate.opsForValue().set(generateTokenCacheKey(userId), token);
        return token;
    }

    /**
     * 验证token
     *
     * @param userId 用户id
     * @param token  token
     * @return true:成功，false:失败
     */
    public static boolean validateToken(int userId, String token) {
        String tokenInCache = staticRedisTemplate.opsForValue().get(generateTokenCacheKey(userId));
        return token.equals(tokenInCache);
    }

    /**
     * 生成token缓存key
     * 根据用户id生成用户对应的token在缓存中的key
     *
     * @param userId 用户id
     * @return 缓存中的key
     */
    private static String generateTokenCacheKey(long userId) {
        return "token:" + userId;
    }

}
