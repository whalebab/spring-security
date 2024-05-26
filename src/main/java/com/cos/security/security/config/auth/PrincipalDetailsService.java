package com.cos.security.security.config.auth;

import com.cos.security.security.domain.User;
import com.cos.security.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public PrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //1. 시큐리티 session = Authentication = UserDetails
    //2. 시큐리티 session = Authentication(내부 UserDetails)
    //3. 시큐리티 session(Authentication(내부 UserDetails)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username ={}", username);
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
