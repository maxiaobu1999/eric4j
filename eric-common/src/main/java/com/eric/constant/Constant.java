package com.eric.constant;

import java.util.Collections;
import java.util.List;

public class Constant {
    /**
     * 用户token类型
     */
    public static final String JWT_USER_TOKEN = "jwt-user-token-type-key";

    /**
     * 用户名称 key
     */
    public static final String JWT_USER_NAME = "jwt-user-name-key";

    /**
     * 用户名称 key
     */
    public static final String JWT_USER_ID = "jwt-user-id-key";

    /**
     * 权限 key
     */
    public static final String JWT_PERMISSIONS_KEY = "jwt-permissions-key_";

    /**
     * 角色 key
     */
    public static final String JWT_ROLES_KEY = "jwt-roles-key_";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_LIST = "jwt-refresh-token-list:";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt-refresh-token-blacklist:";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_DEVICE_BLACKLIST = "jwt-refresh-token-device-blacklist:";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_LIST = "jwt-access-token-list:";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST = "jwt-access-token-blacklist:";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_DEVICE_BLACKLIST = "jwt-access-token-device-blacklist:";


    /**
     * 正常token
     */
    public static final String ACCESS_TOKEN = "authorization";

    /**
     * 刷新token
     */
    public static final String REFRESH_TOKEN = "refresh_token";

    /**
     * 语言
     */
    public static final String LANG = "lang";


// 下面为 shiro 验证 token 的 常量


    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY = "jwt-refresh-key_";

    /**
     * 标记新的access_token
     */
    public static final String JWT_REFRESH_IDENTIFICATION = "jwt-refresh-identification_";

    /**
     * 上面已有
     * access_token 主动退出后加入黑名单 key
     */
    //public static final String JWT_ACCESS_TOKEN_BLACKLIST="jwt-access-token-blacklist_";

    /**
     * 标记用户是否已经被锁定
     */
    public static final String ACCOUNT_LOCK_KEY = "account-lock-key:";

    /**
     * 标记用户是否已经删除
     */
    public static final String DELETED_USER_KEY = "deleted-user-key:";

    /**
     * 部门编码key
     */
    public static final String DEPT_CODE_KEY = "dept-code-key:";

    /**
     * 用户 权鉴 缓存 key
     */
    public static final String IDENTIFY_CACHE_KEY = "com.starPay.shiro.CustomRealm.authorizationCache:";

    /**
     * 用户 验证码 缓存 key
     */
    public static final String VERIFY_CODE_KEY = "com.starPay.CustomRealm.verifyCodeCache:";

    /**
     * 用户权限
     */
    public static final List<String> CUSTOMER_PERMS = Collections.singletonList("customer");

    /**
     * 手机正则
     */
    public static final String PHONE_REGEX = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    /**
     * 手机正则 新加坡手机号,一般是8位，大都是8和9开头的
     */
    public static final String SINGAPORE_PHONE_REGEX = "^([8|9])\\d{7}$";

    /**
     * 手机正则 香港手机号（八位数，6或9开头）
     */
    public static final String HONG_KONG_PHONE_REGEX = "^([6|9])\\d{7}$";

    /**
     * 手机正则 澳门手机号（八位数，6开头）
     */
    public static final String MACAO_PHONE_REGEX = "^[6]\\d{8}$";

    /**
     * 手机正则 台湾手机号（十位数，09开头）
     */
    public static final String TAIWAN_PHONE_REGEX = "^([0][9])\\d{8}$";

    /**
     * 手机验证码时间
     */
    public static final long VERIFY_CODE_CACHE_TIME = 5 * 60 * 1000L;

    /**
     * 手机平台
     */
    public static final String VERIFY_CODE_SMS = "Has in SMS";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "STAR_PAY_DEFAULT";

    /**
     * INTEGER 1
     */
    public static final Integer INTEGER_VALUE_ONE = 1;

    /**
     * 微信全局access_token
     */
    public static final String WX_ACCESS_TOKEN_KEY = "wx:access_token";

    /**
     * 微信ticket
     */
    public static final String WX_TICKET_KEY = "wx:ticket";

    /**
     * 每天访问量
     */
    public static final String DAILY_VISITS_KEY = "app:daily:visits";
}
