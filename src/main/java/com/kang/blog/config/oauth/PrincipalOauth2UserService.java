package com.kang.blog.config.oauth;


import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.config.oauth.provider.FacebookUserInfo;
import com.kang.blog.config.oauth.provider.GoogleUserInfo;
import com.kang.blog.config.oauth.provider.NaverUserInfo;
import com.kang.blog.config.oauth.provider.OAuth2UserInfo;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    //Oauth2 로그인 완료 후 후처리 - 구글,페이스북으로 받은 userRequest 데이터 후처리.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        System.out.println("ClientRegistration = " + userRequest.getClientRegistration()); //Registration ID로 어떤 Oauth로 로그인했는지 확인 가능(구글,페이스북)
        System.out.println("AccessToken = " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User= super.loadUser(userRequest);
        System.out.println("Attributes = " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo=null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else{
            System.out.println("우리는 구글과 페이스북, 네이버만 지원해요");
        }

        String provider = oAuth2UserInfo.getProvider();                               //ex) google,facebook
        String providerID = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider+"_"+providerID;     // ex) google_123212345245245
        String password = encoder.encode("아무비밀번호");

        User userEntity = userRepository.findByUsername(username);
        if (userEntity==null){
            System.out.println("첫 로그인입니다");
            userEntity = User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(RoleType.USER)
                    .provider(provider)
                    .providerId(providerID)
                    .build();
            userRepository.save(userEntity);
        }else{
            System.out.println("로그인을 이미 한적이 있습니다. 당신은 자동 로그인됩니다.");
        }

        //회원가입 강제 진행.
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes()); //이 객체는 Autentication 객체에 들어감.
    }
    //해당 매소드 종료시 @AuthenticationPrincipal 이 생성된다.
}
//구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 구글 로그인 완료 -> code를 OAuth-client가 받음 -> AccessToken 요청하고 받음
//-> userRequest정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다.
