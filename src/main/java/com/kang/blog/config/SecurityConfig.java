package com.kang.blog.config;

import com.kang.blog.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration        //빈 등록
@EnableWebSecurity   //시큐리티 필터 설정,등록
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 특정 주소로 접근 시, 권한 및 인증을 미리 체크.
public class SecurityConfig{

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()                           //csrf 토큰 비활성화.
                .authorizeHttpRequests()                    // 요청이 들어올 때,
                .antMatchers("/auth/**","/","/js/**","/css/**","/img/**","/test/**")        // auth/이하 모든 경로는
                .permitAll()                                // 미인증, 인증자 모두 접속 허용
                .anyRequest()                               // 그외 모든 요청은
                .authenticated()                            // 인증 필수이다.
                .and()
                .formLogin()
                .loginPage("/auth/loginForm")              // 로그인 페이지 주소 설정.
                .loginProcessingUrl("/auth/login")        //스프링 시큐리티가 해당 주소로 요청오는 로그인 가로채고 대신 로그인.
                .defaultSuccessUrl("/")                  //로그인 성공 시 이동 경로.
                .and()
                .oauth2Login()
                .loginPage("/auth/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);  //Oauth 로그인 완료 후 후처리(엑세스 토큰, 사용자 프로필 정보 받기)

        return http.build();
    }
}
