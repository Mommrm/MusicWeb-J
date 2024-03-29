package com.example.demo.config;

import com.example.demo.util.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//跨域通用
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")      //允许跨域访问的路径
                .allowedOriginPatterns("*")              //允许跨域访问的源
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")  //运行请求方法
                .maxAge(168000)             //预检间隔时间
                .allowedHeaders("*")        //允许头部设置
                .allowCredentials(true);  //是否发送Cookie
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(
                "/user/**",
                "/playlist/*",
                "/comment/*",
                "/song/*"
        );
    }
}
