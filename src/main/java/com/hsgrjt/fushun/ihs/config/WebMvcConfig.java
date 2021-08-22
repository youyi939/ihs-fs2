package com.hsgrjt.fushun.ihs.config;

import com.hsgrjt.fushun.ihs.interceptors.IhsAuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    IhsAuthenticationInterceptor tokenInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
//                .allowedOrigins("http://192.168.1.10:8081", "https://192.168.1.10:8081", "http://localhost:8081", "https://localhost:8081", "http://api.mishowme.com:8081", "https://api.mishowme.com:8081")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(168000)
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 设置所有的路径都要进行拦截
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }

}
