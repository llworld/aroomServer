package com.aroominn.aroom.config;

import com.aroominn.aroom.AroomApplication;
import com.aroominn.aroom.utils.filter.AppInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    AppInterceptor AppInterceptor() {
        return new AppInterceptor();
    }

    @Autowired
    AppInterceptor appInterceptor;

   @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 可添加多个，这里选择拦截所有请求地址，进入后判断是否有加注解即可
        registry.addInterceptor(appInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/**/login","/api/**/add","/api/jiguang/**","/test");
        super.addInterceptors(registry);
    }

    /**
     * 访问外部文件配置，访问D盘下文件
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置server虚拟路径，handler为jsp中访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/resource/images/**")
                .addResourceLocations("file:./images/");
    }

}
