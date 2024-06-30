package com.eric.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eric.constant.Constant;
import com.eric.redis.RedisUtils;
import com.eric.redis.TokenManager;
import com.eric.utils.StringUtils;
//import lombok.Data;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/***
 * jwt 工具类
 * @author power pay
 * @since 2024-02-27
 */
//@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 默认过期时间5分钟
     */
    private static final long EXPIRE_TIME = 1 * 60 * 1000;

    /**
     * 访问Token时间
     */
    private static Duration accessTokenExpireTime;

    /**
     * 刷新Token时间
     */
    private static Duration refreshTokenExpireTime;

    /**
     * 主题
     */
    private static String issuer;

    /**
     * 验证密码
     */
    private static String secretKey;

    /**
     * 声称用户KEY
     */
    private static String claimName = "user";

    private static RedisUtils redisUtils;

    /**
     * 生成token， 以上三个方法 都调用此方法
     *
     * @param issuer    签发人
     * @param subject   主题
     * @param claims    存储在JWT里面的信息 一般放些用户的权限/角色信息
     * @param ttlMillis 有效时间(毫秒) --》配置文件获取
     * @param secret    秘钥  --》配置文件获取
     * @return 返回 token java.lang.String
     */
    public static String sign(String issuer, String subject, Map<String, Object> claims,
                              long ttlMillis, String secret) {
        Date now = new Date(System.currentTimeMillis());
        Date expDate = new Date(System.currentTimeMillis() + ttlMillis);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 创建 jwt 构造器
        JWTCreator.Builder builder = JWT.create();
        if (null != claims) {
            builder.withClaim(claimName, claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.withSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.withIssuer(issuer);
        }
        // 主题在 此时 进行时间设定
        builder.withIssuedAt(now);
        builder.withExpiresAt(expDate);
        return builder.sign(algorithm);
    }



    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        JwtUtils.redisUtils = redisUtils;
    }

    @Value("${jwt.issuer}")
    public void setIssuer(String issuer) {
        JwtUtils.issuer = issuer;
    }

    @Value("${jwt.secretKey}")
    public void setSecretKey(String secretKey) {
        JwtUtils.secretKey = secretKey;
    }

    @Value("${jwt.accessTokenExpireTime}")
    public void setAccessTokenExpireTime(Duration accessTokenExpireTime) {
        JwtUtils.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Value("${jwt.refreshTokenExpireTime}")
    public void setRefreshTokenExpireTime(Duration refreshTokenExpireTime) {
        JwtUtils.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    /**
     * 生成 access sign token
     *
     * @param subject 主题
     * @param claims  存储在JWT里面的信息 一般放些用户的权限/角色信息
     * @return
     */
    public static String accessSign(String subject, Map<String, Object> claims) {
        claims.put(Constant.JWT_USER_TOKEN, Constant.ACCESS_TOKEN);
        return sign(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 生成 新的token并缓存加屏蔽之前的token
     *
     * @param userName 主题
     * @param userId  userId
     * @return token
     */
    public static String accessSign(String userName, Long userId) {
        // 查询是否有缓存，有的话注销
        String lastToken = (String )redisUtils.get(Constant.JWT_ACCESS_TOKEN_LIST + userId);
        if (StringUtils.isNotBlank(lastToken)) {
            blackDeviceAccessToken(lastToken, userId);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_USER_NAME, userName);
        claims.put(Constant.JWT_USER_ID, userId);
        // 放入用户对应角色的权限， 假数据，按说是从数据库查询，已改为从数据库中 获取权限集合
        claims.put(Constant.JWT_PERMISSIONS_KEY, Constant.CUSTOMER_PERMS);
        String token = sign(issuer, userName, claims, accessTokenExpireTime.toMillis(), secretKey);
        // 缓存 token
        redisUtils.set(Constant.JWT_ACCESS_TOKEN_LIST + userId, token, JwtUtils.getRemainingTime(token), TimeUnit.MILLISECONDS);
        return token;
    }

    /**
     * 生成 新的token并缓存加屏蔽之前的token
     *
     * @param userName 主题
     * @param userId  userId
     * @return token
     */
    public static String refreshSign(String userName, Long userId) {
        // 查询是否有缓存，有的话注销
        String lastToken = (String ) redisUtils.get(Constant.JWT_ACCESS_TOKEN_LIST + userId);
        if (StringUtils.isNotBlank(lastToken)) {
            blackDeviceRefreshToken(lastToken, userId);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_USER_NAME, userName);
        claims.put(Constant.JWT_USER_ID, userId);
        // 放入用户对应角色的权限， 假数据，按说是从数据库查询，已改为从数据库中 获取权限集合
        claims.put(Constant.JWT_PERMISSIONS_KEY, Constant.REFRESH_TOKEN);
        String token = sign(issuer, userName, claims, refreshTokenExpireTime.toMillis(), secretKey);
        // 缓存 token
        redisUtils.set(Constant.JWT_REFRESH_TOKEN_LIST + userId, token, JwtUtils.getRemainingTime(token), TimeUnit.MILLISECONDS);
        return token;
    }

    public static void blackAccessToken(String accessToken, Long userId) {
        redisUtils.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken, userId,
                JwtUtils.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
    }

    public static void blackDeviceAccessToken(String accessToken, Long userId) {
        redisUtils.set(Constant.JWT_ACCESS_TOKEN_DEVICE_BLACKLIST + accessToken, userId,
                JwtUtils.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
    }

    public static void blackRefreshToken(String accessToken, Long userId) {
        redisUtils.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + accessToken, userId,
                JwtUtils.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
    }

    public static void blackDeviceRefreshToken(String accessToken, Long userId) {
        redisUtils.set(Constant.JWT_REFRESH_TOKEN_DEVICE_BLACKLIST + accessToken, userId,
                JwtUtils.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
    }

    /**
     * 生成 refresh sign token
     *
     * @param subject 主题
     * @param claims  存储在JWT里面的信息 一般放些用户的权限/角色信息
     * @return
     */
    public static String refreshSign(String subject, Map<String, Object> claims) {
        claims.put(Constant.JWT_USER_TOKEN, Constant.REFRESH_TOKEN);
        return sign(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
    }



    /**
     * 从令牌中获取 数据声明 Claims
     *
     * @param token 传入的 jwt
     * @return Claims
     */
    public static Map<String, Claim> claimsFromToken(String token) {
        DecodedJWT jwt = decodedJWT(token);
        return jwt.getClaims();
    }

    /**
     * 从令牌中获取 解析数据声明
     *
     * @param token 传入的 jwt
     * @return Claims
     */
    private static DecodedJWT decodedJWT(String token) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return jwt;
    }

    /***
     * 获取用户Id
     * @param token
     * @return 用户Id
     */
    public static Long getUserId(String token) {
        DecodedJWT jwt = decodedJWT(token);
        Map<String, Claim> claims = jwt.getClaims();
        Map<String, Object> user = claims.get(claimName).asMap();
        Object jwtUserId = user.get(Constant.JWT_USER_ID);
        return castLongUserId(jwtUserId);
    }

    private static Long castLongUserId(Object jwtUserId) {
        Long userId;
        if (jwtUserId instanceof Integer) {
            userId = ((Integer) jwtUserId).longValue();
        } else if (jwtUserId instanceof Long) {
            userId = (Long) jwtUserId;
        } else {
            userId = null;
        }
        return userId;
    }

    public static String getUserName(String token) {
        DecodedJWT jwt = decodedJWT(token);
        Map<String, Claim> claims = jwt.getClaims();
        Map<String, Object> user = claims.get(claimName).asMap();
        return (String) user.get(Constant.JWT_USER_NAME);
    }

    /***
     * 验证 token 正确性
     * @param token
     * @param userId 用户Id
     * @return 正确为 true 不正确为 false
     */
    public static boolean verify(String token, Long userId) {
        Long uid = getUserId(token);
        return userId.equals(uid);
    }

    /**
     * 验证token 是否过期(true:已过期 false:未过期)
     *
     * @param token
     * @return java.lang.Boolean
     */
    public static Boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = decodedJWT(token);
            Date expiration = jwt.getExpiresAt();
            // token的过期时间 与 当前时间比较，如果小于，则为 true，为过期
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("error={}", e.getLocalizedMessage());
            return true;
        }
    }

    /**
     * 获取token的剩余过期时间
     *
     * @param token
     * @return
     */
    public static long getRemainingTime(String token) {
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            DecodedJWT jwt = decodedJWT(token);
            Date expiration = jwt.getExpiresAt();
            result = expiration.getTime() - nowMillis;
        } catch (Exception e) {
            log.error("error={}", e.getLocalizedMessage());
        }
        return result;
    }

    public static long getIat(String token) {
        long result = System.currentTimeMillis();
        try {
            DecodedJWT jwt = decodedJWT(token);
            Date expiration = jwt.getIssuedAt();
            result = expiration.getTime();
        } catch (Exception e) {
            log.error("error={}", e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 获取token的剩余过期时间
     *
     * @param token token
     * @return 日期
     */
    public static Date getExpireTime(String token) {
        try {
            DecodedJWT jwt = decodedJWT(token);
            return jwt.getExpiresAt();
        } catch (Exception e) {
            log.error("error={}", e.getLocalizedMessage());
        }
        return null;
    }

    /***
     * 验证 token 正确性
     *
     * @param token token
     * @return 正确为 true 不正确为 false
     */
    public static boolean verify(String token) {
        DecodedJWT jwt = decodedJWT(token);
        if (Objects.isNull(jwt)) {
            return false;
        }
        Map<String, Claim> claims = jwt.getClaims();
        return (null != claims && !isTokenExpired(token));
    }
}
