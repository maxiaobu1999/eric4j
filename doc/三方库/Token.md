
# 身份认证
1、redis数据库存储token数据
2、JWT 创建解析校验token，在token编入用户信息，超时等信息
3、shiro 处理哪些api&页面拦截token



## redis
### Spring集成
```shell
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
```
application.yml 中配置
自定义配置类 RedisConfig
### 使用
1. RedisConfig 创建RedisTemplate对象，用于操作redis数据库，RedisUtils封装工具类
```shell
@Autowired
private RedisTemplate redisTemplate;
redisTemplate.boundValueOps("StringKey").set("StringValue");// 通过redisTemplate设置值
String str1 = (String) redisTemplate.boundValueOps("StringKey").get();// 获取值
redisTemplate.delete(key);// 删除单个key
redisTemplate.expire(key,time,TimeUnit.MINUTES);// 指定key的失效时间
redisTemplate.hasKey(key); // 判断key是否存在
```
参考：[RedisTemplate操作Redis](https://blog.csdn.net/lydms/article/details/105224210)

## jwt 生成解析token字符串

## shiro
todo AccessControlFilter

权限框架，处理登录认证、会话管理
- 页面拦截的设置，系统中有些页面可以直接访问，有些页面需要登录才能访问，有些页面不仅需要登录还需要登录的用户有相关权限才能访问
- 如果用户没登录直接访问需要登录或需要权限的页面，则调转到登录页面让用户登录
- 用户登录认证后，访问需要权限的页面时，系统可以识别该用户是谁并鉴权。
### 集成 Configuration
``` shell
            <!-- Shiro核心框架 -->
            <dependency>
                <groupId>org.apache.shiro</groupId>
                <artifactId>shiro-core</artifactId>
                <version>${shiro.version}</version>
            </dependency>
            <!-- Shiro使用Spring框架 -->
            <dependency>
                <groupId>org.apache.shiro</groupId>
                <artifactId>shiro-spring</artifactId>
                <version>${shiro.version}</version>
            </dependency>
            <!-- Shiro使用EhCache缓存框架 -->
            <dependency>
                <groupId>org.apache.shiro</groupId>
                <artifactId>shiro-ehcache</artifactId>
                <version>${shiro.version}</version>
            </dependency>
```
application.yml
```shell
shiro:
  user:
    # 登录地址
    loginUrl: /login
    # 权限认证失败地址
    unauthorizedUrl: /unauth
    # 首页地址
    indexUrl: /index
    # 验证码开关 TODO-2023
    captchaEnabled: false
    # 验证码类型 math 数字计算 char 字符验证
    captchaType: math
  cookie:
    # 设置Cookie的域名 默认空，即当前访问的域名
    domain:
    # 设置cookie的有效访问路径
    path: /
    # 设置HttpOnly属性
    httpOnly: true
    # 设置Cookie的过期时间，天为单位
    maxAge: 30
    # 设置密钥，务必保持唯一性（生成方式，直接拷贝到main运行即可）Base64.encodeToString(CipherUtils.generateNewKey(128, "AES").getEncoded()) （默认启动生成随机秘钥，随机秘钥会导致之前客户端RememberMe Cookie无效，如设置固定秘钥RememberMe Cookie则有效）
    cipherKey:
  session:
    # Session超时时间，-1代表永不过期（默认30分钟）
    expireTime: 30
    # 同步session到数据库的周期（默认1分钟）
    dbSyncPeriod: 1
    # 相隔多久检查一次session的有效性，默认就是10分钟
    validationInterval: 10
    # 同一个用户最大会话数，比如2的意思是同一个账号允许最多同时两个人登录（默认-1不限制）
    maxSession: -1
    # 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
    kickoutAfter: false
  rememberMe:
    # 是否开启记住我
    enabled: false

```

编写一个 ShiroConfig 类
- 1、ShiroFilterFactoryBean 配置需要拦截的页面
- 2、创建设置securityManager： shiroFilterFactoryBean.setSecurityManager(securityManager);
- 3、创建设置数据源 Realm AuthorizingRealm ： UserRealmsecurityManager.setRealm(userRealm);
- 4、Realm重写doGetAuthenticationInfo 实现验证规则
- AccessControlFilter 用来校验token
- 5、集成完毕，验证token
```shell
UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
Subject subject = SecurityUtils.getSubject();
subject.login(token);
```


## shiro
Subject：主体，可以看到主体可以是任何可以与应用交互的 “用户”；

SecurityManager：相当于 SpringMVC 中的 DispatcherServlet 或者 Struts2 中的 FilterDispatcher；是 Shiro 的心脏；所有具体的交互都通过 SecurityManager 进行控制； 它管理着所有 Subject、且负责进行认证和授权、及会话、缓存的管理。

Authenticator：认证器，负责主体认证的，这是一个扩展点，如果用户觉得 Shiro 默认的不好，可以自定义实现；其需要认证策略（Authentication Strategy），即什么情况下算用户认证通过了；

Authrizer：授权器，或者访问控制器，用来决定主体是否有权限进行相应的操作；即控制着用户能访问应用中的哪些功能；

Realm：可以有 1 个或多个 Realm，可以认为是安全实体数据源，即用于获取安全实体的；可以是 JDBC 实现，也可以是 LDAP 实现，或者内存实现等等；由用户提供；注意：Shiro 不知道你的用户 / 权限存储在哪及以何种格式存储；所以我们一般在应用中都需要实现自己的 Realm；

SessionManager：如果写过 Servlet 就应该知道 Session 的概念，Session 呢需要有人去管理它的生命周期，这个组件就是 SessionManager；而 Shiro 并不仅仅可以用在 Web 环境，也可以用在如普通的 JavaSE 环境、EJB 等环境；所有呢，Shiro 就抽象了一个自己的 Session 来管理主体与应用之间交互的数据；这样的话，比如我们在 Web 环境用，刚开始是一台 Web 服务器；接着又上了台 EJB 服务器；这时想把两台服务器的会话数据放到一个地方， 这个时候就可以实现自己的分布式会话（如把数据放到 Memcached 服务器）；

SessionDAO：DAO 大家都用过，数据访问对象，用于会话的 CRUD，比如我们想把 Session 保存到数据库，那么可以实现自己的 SessionDAO，通过如 JDBC 写到数据库； 比如想把 Session 放到 Memcached 中，可以实现自己的 Memcached SessionDAO；另外 SessionDAO 中可以使用 Cache 进行缓存，以提高性能；

CacheManager：缓存控制器，来管理如用户、角色、权限等的缓存的；因为这些数据基本上很少去改变，放到缓存中后可以提高访问的性能

Cryptography：密码模块，Shiro 提高了一些常见的加密组件用于如密码加密 / 解密的。

## 参考资料
最简明的Shiro教程
https://zebinh.github.io/2020/03/SimpleShiro/
整合shiro 目录
https://github.com/YuxingXie/shiro
https://waylau.com/apache-shiro-1.2.x-reference/II.%20Core%20%E6%A0%B8%E5%BF%83/5.%20Authentication%20%E8%AE%A4%E8%AF%81.html

## 参考资料
JWT介绍以及java-jwt的使用
https://blog.csdn.net/oscar999/article/details/102728303
什么是 Refresh Token
https://docs.authing.cn/v2/concepts/

Shiro github示例
https://github.com/zhangtianyi0110/Spring-All/blob/master/11-SpringBoot-Shiro-Authentication/SpringBoot-Shiro%E5%8A%A0%E5%AF%86%E8%AE%A4%E8%AF%81.md