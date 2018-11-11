package com.example.Genius.intercepter;

import com.example.Genius.DAO.LoginTicketMapper;
import com.example.Genius.DAO.UserMapper;
import com.example.Genius.model.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginRequiredInterceptor.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserMapper userMapper;
    @Autowired
    LoginTicketMapper loginTicketMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if(hostHolder.getCurrentUser() == null)
           response.sendRedirect("/registerAndLogin?next=" + request.getRequestURI());
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
