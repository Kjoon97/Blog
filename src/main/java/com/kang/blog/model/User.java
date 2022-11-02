package com.kang.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Jpa의 기본 전략이 아닌 연결된 DB의 넘버링 전략 따름.(mysql이면 auto_increment, oracle이면 sequence사용)
    private int id;

    @Column(nullable = false, length = 50)  //50자 제한
    private String username;

    @Column(nullable = false, length = 100)  //비번 해쉬화하기 위해 넉넉히 100자.
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String provider;        //Oauth2 로그인한 사이트 이름
    private String providerId;      //Oauth2 로그인한 사이트 sub ID

    @CreationTimestamp
    private Timestamp createDate;            //repository.save() 후-> 데이터 createDate에 저장 후 -> db에 insert.

    public void setRoleAndEncPassword(RoleType role, String password){
        this.role = role;
        this.password = password;
    }
}
