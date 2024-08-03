# Spring

同Spring framework，一系列java开发框架，主要用于java服务端
Spring Core：Spring核心模块，主要提供 ioC 依赖注入
Spring Context：向Spring框架提供上下文信息、Spring AOP：面向切面编程，为基于 Spring 的应用程序中的对象提供了事务管理服务
Spring JDBC：Java数据库连接
Spring JMS：Java消息服务
Spring ORM：用于支持 MyBatis、Hibernate 等 ORM 工具
Spring Web：为创建Web应用程序提供支持
Spring Test：提供了对 JUnit 和 TestNG 测试的支持
Spring Aspects：该模块为与AspectJ的集成提供支持。
Spring Web：Spring框架支持与Struts集成，为基于web的应用程序提供了上下文。

## Spring 核心

### 控制反转

IOC——Inversion of Control，指的是将对象(bean)的创建权交给 Spring 去创建, IocContainer 保存管理bean，依赖注入给应用使用。
控制反转是设计思想，依赖注入是实现方式

#### 依赖注入

DI——Dependency Injection，是指依赖的对象不需要手动调用 setXX 方法去设置，而是通过配置赋值。

### 面向切面

AOP —— Aspect Oriented Programming
拦截加注解的方法，加log，这个方法即切面

### 实现原理

运行时注解，反射，黄油刀getAnnotation找注解，读注解findViewById，反射给view赋值
编译时注解，Processor process()加字符串
tip：
元注解，注解使用的注解
@Target 可修饰的对象范围，@Retention解保留的时间范围，@Documented
生成帮助文档时是否要保留其注解信息。，@Inherited具有继承性  
https://pdai.tech/md/java/basic/java-basic-x-annotation.html#%E5%85%83%E6%B3%A8%E8%A7%A3

## SpringMVC

pring MVC是Spring在Spring Container Core和AOP等技术基础上，遵循上述Web MVC的规范推出的web开发框架，目的是为了简化Java栈的web开发。
TODO Servlet

## SpringBoot
脚手架：项目依赖管理
起步依赖
单模块： spring-boot-starter-parent 使用spring的parent模块
多模块：spring-boot-dependencies 定义自己的parent模块  

- tip起步依赖：多模块打包到一起，提供某项功能
[SpringBoot还提供了哪些starter模块呢？](https://pdai.tech/md/spring/springboot/springboot-x-hello-world.html#springboot%E8%BF%98%E6%8F%90%E4%BE%9B%E4%BA%86%E5%93%AA%E4%BA%9Bstarter%E6%A8%A1%E5%9D%97%E5%91%A2)

```SHELL
 <!-- SpringBoot Web容器 内嵌tomcat-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
```

## 参考资料

Spring框架知识体系详解
https://pdai.tech/md/spring/spring.html
