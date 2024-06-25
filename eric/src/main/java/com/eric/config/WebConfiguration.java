package com.eric.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/** 静态资源配置 同时解决跨域问题 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("(?!/static/**)|/**")// SpringMVC拦截，url不包含/static/**并且任何路径
                .allowedOrigins("*","null")
                .allowedMethods("*");// 允许所有请求
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 添加token拦截器
//        registry.addInterceptor(new TokenInterceptor())
//                .excludePathPatterns("/static/**")// 排除静态资源路径
//                .excludePathPatterns("/swagger**/**")// 排除拦截路径
//                .excludePathPatterns("/v2/api-docs");// 排除拦截路径
//        super.addInterceptors(registry);
//    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/news_image")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/news_html")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/mini_author")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/mini_video")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/mini_image")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/tik_video")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/tik_image")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/comment")
                .addResourceLocations("file:/Users/v_maqinglong/Documents/oss/image")
                .addResourceLocations("classpath:/static/")// 指定resources/static目录为静态资源目录
                .setCachePeriod(1);

        registry.addResourceHandler("/source/test/**")
                .addResourceLocations("file:D:/sources/test/")
                .addResourceLocations("file:/home/ecs-assist-user/eric/sources/test/");
        registry.addResourceHandler("/source/avatar/**")
                .addResourceLocations("file:D:/sources/avatar/")
                .addResourceLocations("file:/home/ecs-assist-user/eric/sources/avatar/");
        registry.addResourceHandler("/source/chensheng/**")
                .addResourceLocations("file:D:/sources/chensheng/")
                .addResourceLocations("file:/home/ecs-assist-user/eric/sources/chensheng/");


        // umi的资源文件uri写的 /xxx
        registry.addResourceHandler("/*")
                .addResourceLocations("classpath:/static/");

        /** swagger配置 */
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//        // 解决swagger无法访问
//        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        // 解决swagger的js文件无法访问
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");


    }

}
