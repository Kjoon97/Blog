package com.kang.blog.controller.api;

import com.kang.blog.dto.ResponseDto;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    //회원가입
    @PostMapping("/auth/join")
    public ResponseDto<Integer> save(@RequestBody User user){
        user.setRoleType(RoleType.USER);
        int result = userService.register(user); //회원가입 성공 시 1, 실패 시 -1 반환.
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }
}
