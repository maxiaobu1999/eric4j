package com.eric.shiro;


import com.eric.redis.RedisCacheManager;
import com.eric.shiro.realm.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * Shiro配置类
 *
 * @author zhimin
 */
@Configuration
public class ShiroConfig {
    /**
     * Session超时时间，单位为毫秒（默认30分钟）
     */
    @Value("${shiro.session.expireTime}")
    private int expireTime;

    /**
     * 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
     */
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;

    /**
     * 同一个用户最大会话数
     */
    @Value("${shiro.session.maxSession}")
    private int maxSession;

    /**
     * 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
     */
    @Value("${shiro.session.kickoutAfter}")
    private boolean kickoutAfter;

    /**
     * 验证码开关
     */
    @Value("${shiro.user.captchaEnabled}")
    private boolean captchaEnabled;

    /**
     * 验证码类型
     */
    @Value("${shiro.user.captchaType}")
    private String captchaType;

    /**
     * 设置Cookie的域名
     */
    @Value("${shiro.cookie.domain}")
    private String domain;

    /**
     * 设置cookie的有效访问路径
     */
    @Value("${shiro.cookie.path}")
    private String path;

    /**
     * 设置HttpOnly属性
     */
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    /**
     * 设置Cookie的过期时间，秒为单位
     */
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    /**
     * 设置cipherKey密钥
     */
    @Value("${shiro.cookie.cipherKey}")
    private String cipherKey;

    /**
     * 登录地址
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    /**
     * 权限认证失败地址
     */
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * 是否开启记住我功能
     */
    @Value("${shiro.rememberMe.enabled: false}")
    private boolean rememberMe;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${session.redis.inMemoryTimeout}")
    private int sessionInMemoryTimeout;

    @Value("${session.redis.saveToRedisInterval}")
    private int saveToRedisInterval;
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    public ShiroConfig() {
        logger.info("构造函数 执行");
    }
    /**
     * 创建设置securityManager安全管理器
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm) {
        // 新建一个SecurityManager供ShiroFilterFactoryBean使用
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * 登录的 认证域
     *
     * @param shiroHashedCredentialsMatcher
     * @param redisCacheManager
     * @return
     */
    @Bean(name = "shiroRealm")
    public ShiroRealm getShiroRealm(@Qualifier("shiroHashedCredentialsMatcher") ShiroHashedCredentialsMatcher shiroHashedCredentialsMatcher,
                                    @Qualifier("redisCacheManager") RedisCacheManager redisCacheManager) {
        ShiroRealm shiroRealm = new ShiroRealm();
        // 自定义 处理 token 过滤
        shiroRealm.setCredentialsMatcher(shiroHashedCredentialsMatcher);
        // 自定义 缓存管理器
        shiroRealm.setCacheManager(redisCacheManager);
        return shiroRealm;
    }
    /**
     * Shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager)
    {
        // guide ：1. 初始化一个ShiroFilter工程类
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 2. 我们知道Shiro是通过SecurityManager来管理整个认证和授权流程的，这个SecurityManager可以在下面初始化
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/user/login/username", "anon");
        filterChainDefinitionMap.put("/user/register/username", "anon");
        filterChainDefinitionMap.put("/user/login/v2/username", "anon");
        filterChainDefinitionMap.put("/user/login/v2/username", "anon");
        filterChainDefinitionMap.put("/prod/info", "anon");
        filterChainDefinitionMap.put("/prod/page", "anon");
        filterChainDefinitionMap.put("/prod/newProd", "anon");
        filterChainDefinitionMap.put("/category/info", "anon");
        filterChainDefinitionMap.put("/category/page", "anon");
        filterChainDefinitionMap.put("/category/range", "anon");
        filterChainDefinitionMap.put("/sys/menu/nav", "anon");

        filterChainDefinitionMap.put("/advertisement", "anon");


        //自定义拦截器限制并发人数,参考博客：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("token", new ShiroAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        filterChainDefinitionMap.put("/**", "token,authc");

        // 身份认证失败，则跳转到登录页面的配置
        // 没有登录的用户请求需要登录的页面时自动跳转到登录页面。 配置 shiro 默认登录界面地址，
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 5. 让ShiroFilter按这个规则拦截
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * shiro 的 缓存管理器
     * 需要在 CustomRealm bean 中进行设置
     *
     * @return
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    /**
     * token 的过滤
     * 需要在 CustomRealm bean 中进行设置
     *
     * @return
     */
    @Bean(name = "shiroHashedCredentialsMatcher")
    public ShiroHashedCredentialsMatcher shiroHashedCredentialsMatcher() {
        return new ShiroHashedCredentialsMatcher();
    }


    /**
     * 下面两个配置类，开启 shiro aop 注解 支持.
     * 使用代理方式;所以需要开启代码支持;
     * <p>
     * 如果不加 使用 @RequirePermissions 无效
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    // ===============================================================================

}
