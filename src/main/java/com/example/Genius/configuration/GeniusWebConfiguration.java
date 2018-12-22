package com.example.Genius.configuration;
import com.example.Genius.intercepter.LoginRequiredInterceptor;
import com.example.Genius.intercepter.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class GeniusWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    //    registry.addInterceptor(passportInterceptor);          //此处特别注意拦截器优先级

    //    registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");        // 设置拦截路径

        super.addInterceptors(registry);
    }
}