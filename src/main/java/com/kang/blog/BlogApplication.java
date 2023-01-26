package com.kang.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaAuditing  // 생성/수정 날짜,시간 자동화
@SpringBootApplication
public class BlogApplication {

	//비밀번호 해쉬 인코딩 매소드 빈 등록
	@Bean
	public BCryptPasswordEncoder encodePWD(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
