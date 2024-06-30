# @ComponentScan
组件扫描注解，用来扫描@Controller  @Service  @Repository这类,主要就是定义扫描的路径从中找出标志了需要装配的类到Spring容器中.

#@MapperScan 
是扫描mapper类的注解,就不用在每个mapper类上加@MapperScan了


@Configuration
用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
项目启动时，执行构造函数、@bean注解函数


@Bean
产生一个Bean对象、方法，然后这个Bean对象交给Spring管理

@Qualifier
指定注入哪个bean