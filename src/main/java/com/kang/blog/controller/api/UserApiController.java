package com.kang.blog.controller.api;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.dto.ResponseDto;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    //회원가입
    @PostMapping("/auth/join")
    public ResponseDto<Integer> save(@RequestBody User user){
        userService.register(user); //회원가입 성공 시 1, 실패 시 -1 반환.
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //회원 수정
    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){
        userService.userUpdate(user);

        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));  //Authentication 생성.
        SecurityContextHolder.getContext().setAuthentication(authentication);              //세션에 Authentication 등록.
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
