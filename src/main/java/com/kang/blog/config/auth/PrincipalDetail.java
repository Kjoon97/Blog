package com.kang.blog.config.auth;

import com.kang.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

//스프링 시큐리티가 로그인 요청을 가로채고 로그인을 진행하면
//스프링 시큐리티의 고유 세션 저장소에 UserDetails 타입의 객체(PrincipalDetail)를 저장한다.
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user){
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정이 만료되지 않았는지 확인(true-만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 확인(true-안 잠김)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 만료되었는지 확인(true - 만료 안됨)- false로 되면 로그인 안됨.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 활성화(사용 가능)가 되어있는지 확인(true-활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    //계정의 권한을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{                           //GrantedAuthority는 매소드 한 개만 갖고 있으므로 그냥 람다식 사용.
            return "ROLE_"+user.getRole();
        });       //앞에 꼭 "ROLE_"붙여줘야 시큐리티가 인식함.
        return collectors;
    }
}
