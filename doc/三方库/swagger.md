# swagger 在线API文档

接口文档

## 配置configuration

1\ 依赖

``` shell
            <!-- Swagger3依赖 -->
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-boot-starter</artifactId>
                <version>${swagger.version}</version>
                <exclusions>
                    <exclusion>
                        <groupId>io.swagger</groupId>
                        <artifactId>swagger-models</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
            
  # eric.pom
        <!-- swagger3-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
        </dependency>
        <!-- 防止进入swagger页面报类型转换错误，排除3.0.0中的引用，手动增加1.6.2版本 -->
        <dependency>
            <groupId>io.swagger</groupId>
            <artifactId>swagger-models</artifactId>
            <version>1.6.2</version>
        </dependency>          
```

2.配置类 SwaggerConfig 
3.Swagger 拦截配置 WebConfiguration
4.访问  http://127.0.0.1:8089/swagger-ui/index.html


## 常用注解
@Api：用于修饰Controller类，生成Controller相关文档信息
@ApiOperation：用于修饰Controller类中的方法，生成接口方法相关文档信息
@ApiParam：用于修饰接口中的参数，生成接口参数相关文档信息
@ApiModelProperty：用于修饰实体类的属性，当实体类是请求参数或返回结果时，直接生成相关文档信息
#整合Swa
## 参考资料
springboot Swagger3 更新配置详解
https://cloud.tencent.com/developer/article/1840435
