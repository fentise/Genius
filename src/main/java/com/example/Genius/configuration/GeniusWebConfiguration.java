package com.example.Genius.configuration;
import com.example.Genius.intercepter.LoginRequiredInterceptor;
import com.example.Genius.intercepter.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; //已过时

@Component
public class GeniusWebConfiguration implements WebMvcConfigurer {

    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;
      //TODO: 增加权限拦截，帖子详情页，发帖等操作需要登录权限
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);          //此处特别注意拦截器优先级
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
    }

}
