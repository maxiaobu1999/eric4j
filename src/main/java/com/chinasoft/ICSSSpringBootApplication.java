package com.chinasoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//声明该类是一个SpringBoot引导类
@SpringBootApplication
@MapperScan(value = {"com.chinasoft"})// 指定扫描@Mapper&@Repository的目录
public class ICSSSpringBootApplication {

    //main是java程序的入口
    public static void main(String[] args) {
        //run方法 表示运行SpringBoot的引导类  run参数就是SpringBoot引导类的字节码对象
        ConfigurableApplicationContext run = SpringApplication.run(ICSSSpringBootApplication.class);
        WebSocketServer.setApplicationContext(run);
    }

}
