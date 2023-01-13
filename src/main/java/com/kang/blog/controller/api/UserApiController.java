package com.kang.blog.controller.api;

import com.kang.blog.dto.JoinFormDto;
import com.kang.blog.dto.ResponseDto;
import com.kang.blog.dto.FindPwdDto;
import com.kang.blog.dto.UpdateFormDto;
import com.kang.blog.model.User;
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

        //중복 username 확인.
        if (userService.checkDuplicate(joinFormDto)){
            return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임중복오류");
        }

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

    //비밀번호 찾기(임시 비밀번호 발급)
    @PostMapping("/auth/find")
    public ResponseDto<?> find(@Valid @RequestBody FindPwdDto findPwdDto, BindingResult bindingResult){

        //존재 여부 확인.
        if (userService.checkExist(findPwdDto) != true){
            System.out.println("존재 안함");
            return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"존재안함");
        }

        userService.getTmpPwd(findPwdDto);
        return new ResponseDto<>(HttpStatus.OK.value(), "ok");
    }

}
