package com.kang.blog.dto;

import com.kang.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFormDto {

    private Long id;
    private String username;

    @NotBlank(message="email이 입력되지 않았습니다.")
    private String email;

    @NotBlank(message="password가 입력되지 않았습니다.")
    private String password;

    //DTO->Entity
    public User toEntity(){     //DTO -> 엔티티
        return User.builder()
                .username(username)
                .id(id)
                .email(email)
                .password(password)
                .build();
    }
}
