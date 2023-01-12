package com.kang.blog.service;

import com.kang.blog.dto.JoinFormDto;
import com.kang.blog.dto.ResponseDto;
import com.kang.blog.dto.UpdateFormDto;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    //회원가입.
    @Transactional
    public void register(JoinFormDto joinFormDto) {
        String rawPassword = joinFormDto.getPassword();
        String encPassword = encoder.encode(rawPassword);       //비밀번호 해쉬 인코딩.
        User user = joinFormDto.toEntity();                     //DTO -> 엔티티
        user.setRoleAndEncPassword(RoleType.USER, encPassword);
        userRepository.save(user);
    }

    //중복 회원 확인
    @Transactional(readOnly = true)
    public boolean checkDuplicate(JoinFormDto joinFormDto){
        return userRepository.existsByUsername(joinFormDto.getUsername());
    }

    //회원 수정
    @Transactional
    public User userUpdate(User user){
        System.out.println("user = " + user.getId());
        //영속성 컨텍스트 user 오브젝트를 영속화 시키고 영속화된 user 객체 수정-> 함수 종료 ->트랜잭션 끝(커밋) 후 자동으로 더티체킹.
        User persistenceUser = userRepository.findById(user.getId()).orElseThrow(()-> {
            return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPwd = user.getPassword();
        String encodePwd = encoder.encode(rawPwd);
        persistenceUser.setPwdAndEmail(encodePwd,user.getEmail());

        return persistenceUser;
    }
}
