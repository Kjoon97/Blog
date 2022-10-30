package com.kang.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration        //빈 등록
@EnableWebSecurity   //시큐리티 필터 설정,등록
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 특정 주소로 접근 시, 권한 및 인증을 미리 체크.
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()                    // 요청이 들어올 때,
                .antMatchers("/auth/**","/","/js/**","/css/**","/img/**")        // auth/이하 모든 경로는
                .permitAll()                                // 미인증, 인증자 모두 접속 허용
                .anyRequest()                               // 그외 모든 요청은
                .authenticated()                            // 인증 필수이다.
                .and()
                .formLogin()
                .loginPage("/auth/loginForm");             // 로그인 페이지 주소 설정.
        return http.build();
    }
}
