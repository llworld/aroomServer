package com.aroominn.aroom.config;

import com.aroominn.aroom.utils.filter.AppInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//@EnableWebMvc
//@Configuration
public class WebFileConfig implements WebMvcConfigurer {



    @Bean
    AppInterceptor AppInterceptor() {
        return new AppInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration registration= registry.addInterceptor(AppInterceptor());
        // 可添加多个，这里选择拦截所有请求地址，进入后判断是否有加注解即可
        registry.addInterceptor(new AppInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/login","/add");
        registry.addInterceptor(AppInterceptor());
        registration.addPathPatterns("/**").excludePathPatterns("/add","/login");

    }


 /*   @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 可添加多个，这里选择拦截所有请求地址，进入后判断是否有加注解即可
        registry.addInterceptor(new AppInterceptor()).addPathPatterns("/**");
    }*/

    /**
     * 访问外部文件配置，访问D盘下文件
     */
/*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置server虚拟路径，handler为jsp中访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///E:images/");

    }*/
}
