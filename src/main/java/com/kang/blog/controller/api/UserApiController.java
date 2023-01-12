package com.kang.blog.controller.api;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.dto.JoinFormDto;
import com.kang.blog.dto.ResponseDto;
import com.kang.blog.dto.UpdateFormDto;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    //회원가입
    @PostMapping("/auth/join")
    public ResponseDto<?> save(@Valid @RequestBody JoinFormDto joinFormDto, BindingResult result){
        //조건에 따라 리턴 데이터 타입이 달라지므로 ResponseDto<>에 ?으로 지정해주면 된다.
        log.info("insertMember {} " , joinFormDto.toString());

        //form 입력 값 유효성 검사
        if(result.hasErrors()){
            HashMap<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                String validKeyName = String.format("valid_%s", error.getField());
                errorMap.put(validKeyName, error.getDefaultMessage());   //  key: 필드 값, value : 에러 메세지.
            }

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errorMap);   //상태코드와 에러 필드, 메세지 리턴.
        }

        userService.register(joinFormDto);
        return new ResponseDto<String>(HttpStatus.OK.value(), "ok");
    }

    //회원 수정
    @PutMapping("/user")
    public ResponseDto<?> update(@Valid @RequestBody UpdateFormDto updateFormDto, BindingResult result){

        //form 입력 값 유효성 검사
        if(result.hasErrors()){
            HashMap<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                String validKeyName = String.format("valid_%s", error.getField());
                errorMap.put(validKeyName, error.getDefaultMessage());   //  key: 필드 값, value : 에러 메세지.
            }

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errorMap);   //상태코드와 에러 필드, 메세지 리턴.
        }

        //회원 수정.
        User user = updateFormDto.toEntity();
        userService.userUpdate(user);

        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));  //Authentication 생성.
        SecurityContextHolder.getContext().setAuthentication(authentication);              //세션에 Authentication 등록.
        return new ResponseDto<String>(HttpStatus.OK.value(), "ok");
    }
}
