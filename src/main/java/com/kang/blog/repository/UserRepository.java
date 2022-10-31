package com.kang.blog.repository;

import com.kang.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//자동으로 빈 등록 되어 @Repository 생략 가능.
public interface UserRepository extends JpaRepository<User, Integer> {  //<user엔티티, pk타입>

    User findByUsername(String username);
}
