package com.example.Genius.controller;

import com.example.Genius.utils.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局系统异常处理*/
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String errorHandler(Exception exception) {
        return "";
    }

    /**
     * 全局自定义异常处理*/
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public String myErrorHandler(MyException ex) {
        return "";
    }

}