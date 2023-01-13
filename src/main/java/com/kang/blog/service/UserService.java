package com.kang.blog.service;

import com.kang.blog.dto.FindPwdDto;
import com.kang.blog.dto.JoinFormDto;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendFrom;

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

    //임시 비밀번호 발급
    @Transactional
    public void getTmpPwd(FindPwdDto findPwdDto) {

        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        String tmpPwd = "";

        // 문자 배열에서 랜덤으로 10개를 뽑아 구문 작성.
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());  //36x랜덤 소수
            tmpPwd += charSet[idx];
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(findPwdDto.getEmail());
            message.setFrom(sendFrom);
            message.setSubject("블로그 임시 비밀번호 안내 이메일입니다.");
            message.setText("안녕하세요.\n"
                    + "블로그 임시비밀번호 안내 관련 이메일 입니다.\n"
                    + "임시 비밀번호를 발급하오니 블로그에 접속하셔서 로그인 하신 후\n"
                    + "반드시 비밀번호를 변경해주시기 바랍니다.\n\n"
                    + "임시 비밀번호 : " + tmpPwd);
            javaMailSender.send(message);
        } catch (MailParseException e) {
            e.printStackTrace();
        } catch (MailAuthenticationException e) {
            e.printStackTrace();
        } catch (MailSendException e) {
            e.printStackTrace();
        } catch (MailException e) {
            e.printStackTrace();
        }

        User user = userRepository.findByUsername(findPwdDto.getUsername());

        //임시 비밀 번호 인코딩해서 변경.
        user.changePwd(encoder.encode(tmpPwd));
    }

    //회원 존재 여부 확인
    @Transactional(readOnly = true)
    public boolean checkExist(FindPwdDto findPwdDto){
        return userRepository.existsByUsername(findPwdDto.getUsername());
    }
}
