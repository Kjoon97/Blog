package com.kang.blog.handler;

import com.kang.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)          //모든 예외 탐지
    public ResponseDto<String> handleException(Exception e){
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        //return "<h1>"+e.getMessage()+"</h1>";          //예외 메세지 출력
    }
}
