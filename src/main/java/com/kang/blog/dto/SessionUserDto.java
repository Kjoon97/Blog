package com.kang.blog.dto;

import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUserDto implements Serializable {  //직렬화 가능한 세션 저장 용 DTO
    private Long id;
    private String username;
    private String email;
    private String password;
    private RoleType role;

    //생성자: 엔티티에서 DTO로 변환
    public SessionUserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
