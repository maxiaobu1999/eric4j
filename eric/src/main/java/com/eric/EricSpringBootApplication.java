package com.eric;

//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

//声明该类是一个SpringBoot引导类
@ServletComponentScan // druid 配置
@SpringBootApplication
@MapperScan(value = {"com.eric"})// 指定扫描@Mapper&@Repository的目录
public class EricSpringBootApplication {

    //main是java程序的入口
    public static void main(String[] args) {
        //run方法 表示运行SpringBoot的引导类  run参数就是SpringBoot引导类的字节码对象
        ConfigurableApplicationContext run = SpringApplication.run(EricSpringBootApplication.class);
        WebSocketServer.setApplicationContext(run);
    }

}
