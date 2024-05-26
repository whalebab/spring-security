package com.cos.security.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated() // user 인증만 되면 /user 하위에 있는 주소는 모두 들어갈 수 있음
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // manager 하위에 있는 주소는 ADMIN, MANAGER 권한만 접근가능
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // admin 하위에 있는 주소는 ADMIN 권한만 접근가능
                .anyRequest().permitAll() // 나머지 주소는 모든 권한이 허용이되어 있음
                .and()
                .formLogin().loginPage("/loginForm") // 권한이 없는 페이지로 이동할때 login 페이지로 이동
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                .defaultSuccessUrl("/"); // 로그인이 완료되면 메인페이지로 이동

    }
}
