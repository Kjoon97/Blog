package com.kang.blog.config.auth;

import com.kang.blog.dto.SessionUserDto;
import com.kang.blog.model.User;
import com.kang.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    //스프링이 로그인 요청 가로챌 때, username, password 변수 2개 가로챔.
    //password 처리는 알아서 해주고, username이 DB에 있는지 확인하면 됨.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        //로그인 예외 처리 
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        SessionUserDto sessionUserDto = new SessionUserDto(user);  //직렬 가능한 user DTO 생성.
        return new PrincipalDetails(sessionUserDto);   // 시큐리티 세션에 UserDetail 타입으로 유저 정보 저장.
    }

    //password는 시큐리티가 로그인 시 비밀번호 가로채고, 회원가입할 때의 비번 해쉬값과 현재 DB 해쉬 값 비교해서 로그인 결정한다.
}
