package com.kang.blog.service;

import com.kang.blog.dto.FindPwdDto;
import com.kang.blog.dto.JoinFormDto;
import com.kang.blog.dto.UpdateFormDto;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired BCryptPasswordEncoder encoder;

    //테스트 종료 후 db 초기화
    @After
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void Join_Test() {
        //given
        String name ="name";
        String email ="jh342@naver.com";
        String password ="1q2w3e4r**";
        JoinFormDto joinFormDto = JoinFormDto.builder()
                .username(name)
                .email(email)
                .password(password)
                .build();
        //when
        userService.register(joinFormDto);
        User savedUser = userRepository.findByUsername(name);
        //then
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getPassword()).isNotEqualTo(password);

    }

    @Test
    @DisplayName("회원 중복 여부 확인 테스트")
    void duplicate_User_Test() {
        //given
        String name ="name";
        String email ="jh342@naver.com";
        String password ="1q2w3e4r**";
        JoinFormDto joinFormDto = JoinFormDto.builder().username(name).email(email).password(password).build();
        userService.register(joinFormDto);
        //when
        boolean check = userService.checkDuplicate(joinFormDto);
        //then
        assertThat(check).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void userUpdate() {
        //given
        String name ="name";
        String email ="jh342@naver.com";
        String password ="1q2w3e4r**";
        JoinFormDto joinFormDto = JoinFormDto.builder().username(name).email(email).password(password).build();
        userService.register(joinFormDto);
        //when
        User user = userRepository.findByUsername("name");
        UpdateFormDto updateFormDto = UpdateFormDto.builder().id(user.getId()).username("name").email("newEamil@naver.com").password("123132**").build();
        User updateUser = updateFormDto.toEntity();
        userService.userUpdate(updateUser);
        //then
        User testUser = userRepository.findByUsername("name");
        assertThat(testUser.getEmail()).isEqualTo("newEamil@naver.com");
    }
}