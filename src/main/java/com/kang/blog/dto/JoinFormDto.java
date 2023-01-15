package com.kang.blog.dto;

import com.kang.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JoinFormDto {

    @NotBlank(message = "유저 네임은 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    //DTO->Entity
    public User toEntity(){     //DTO -> 엔티티
        return User.builder()
                .username(username)
                .email(email)
                .build();
    }

}
