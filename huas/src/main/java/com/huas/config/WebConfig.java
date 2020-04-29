package com.huas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.huas.interceptor.AdminsterLevelInterceptor;
import com.huas.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
     //登录拦截
    @Autowired
    private LoginInterceptor interceptor;
    @Autowired
    private AdminsterLevelInterceptor ainterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// 添加拦截器，配置拦截地址
        registry.addInterceptor(interceptor).addPathPatterns("/student/**");
          registry.addInterceptor(interceptor).addPathPatterns("/user/admin/**");
        registry.addInterceptor(interceptor).addPathPatterns("/user/manager/**");
        registry.addInterceptor(ainterceptor).addPathPatterns("/user/admin/**");
    }
    
}

