package com.kang.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)          //모든 예외 탐지
    public String handleException(Exception e){
        return "<h1>"+e.getMessage()+"</h1>";          //예외 메세지 출력
    }
}
