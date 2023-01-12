package com.kang.blog.aop;


import com.kang.blog.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

@Component
@Aspect
@Slf4j
public class BindingAdvice {

    //UserApiController 중에 인자 개수 상관x, 모든 매소드 앞뒤로 적용.
    @Around("execution(* com.kang.blog.controller.api.UserApiController.*(..))")
    public Object validCheck(ProceedingJoinPoint pJoinPoint) throws Throwable{
        String type = pJoinPoint.getSignature().getDeclaringTypeName();  //클래스
        String method = pJoinPoint.getSignature().getName();            //메소드

        log.info("클래스: {}",type);
        log.info("method: {}",method);

        Object [] args = pJoinPoint.getArgs();  // 해당 메소드 인자 값들.
        for (Object arg : args) {
            if(arg instanceof BindingResult){    // arg가 BindingResult 타입이면(validation 체크 시 문제 있는 경우) 동작
                BindingResult bindingResult = (BindingResult) arg;

                //form 입력 값 유효성 검사
                if(bindingResult.hasErrors()){
                    HashMap<String, String> errorMap = new HashMap<>();

                    for(FieldError error: bindingResult.getFieldErrors()){
                        String validKeyName = String.format("valid_%s", error.getField());
                        errorMap.put(validKeyName, error.getDefaultMessage());   //  key: 필드 값, value : 에러 메세지.
                    }

                    return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errorMap);   //상태코드와 에러 필드, 메세지 리턴.
                }
            }
        }
        return pJoinPoint.proceed();  //함수 프록시 유효성 검사하고 아무 문제x -> 함수 원본의 내부 스택(로직)을 실행
    }
}
