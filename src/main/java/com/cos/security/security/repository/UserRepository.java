package com.cos.security.security.repository;

import com.cos.security.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고있음
// @Repository라는 어노테이션이 없어도 IoC되고 JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy규칙 -> Username문법
    // select * from user where username = ?
    User findByUsername(String username);

    // select * from user where email = ?
    // User findByEmail(String email);
}
