package com.kang.blog.dto;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class JoinFormDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init(){
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close(){
        factory.close();
    }

    @Test
    @DisplayName("회원 가입 유효성 검사 테스트")
    void joinForm_validation_Test(){
        String name="";
        String password="dks6645**";
        String email="jh8q3420@naver.com";

        JoinFormDto joinFormDto = JoinFormDto.builder()
                .password(password)
                .email(email)
                .build();

        Set<ConstraintViolation<JoinFormDto>> validate = validator.validate(joinFormDto);
        Assertions.assertThat(validate).isNotEmpty();
        validate
                .forEach(error -> {
                    Assertions.assertThat(error.getMessage()).isEqualTo("유저 네임은 필수 입력 값입니다.");
                });
    }
}