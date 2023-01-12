package com.kang.blog.controller.api;

import com.kang.blog.dto.JoinFormDto;
import com.kang.blog.dto.ResponseDto;
import com.kang.blog.dto.UpdateFormDto;
import com.kang.blog.model.User;
import com.kang.blog.repository.UserRepository;
import com.kang.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

        userService.register(joinFormDto);
        return new ResponseDto<String>(HttpStatus.OK.value(), "ok");
    }

    //회원 수정
    @PutMapping("/user")
    public ResponseDto<?> update(@Valid @RequestBody UpdateFormDto updateFormDto, BindingResult result){

        //회원 수정.
        User user = updateFormDto.toEntity();
        userService.userUpdate(user);

        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));  //Authentication 생성.
        SecurityContextHolder.getContext().setAuthentication(authentication);              //세션에 Authentication 등록.
        return new ResponseDto<String>(HttpStatus.OK.value(), "ok");
    }
}
