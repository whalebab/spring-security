package com.cos.security.security.controller;

import com.cos.security.security.domain.User;
import com.cos.security.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class IndexController {

    // @ResponseBody는 웹 페이지 내에서 주소가 잘 동작하는지 확인하기 위해 붙여줌

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public IndexController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    // 스프링시큐리티가 해당 주소를 낚아채버림 -> SecurityConfig 파일 생성 후 해당 주소를 낚아채지않음
    @GetMapping("/loginForm")
    //@ResponseBody
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    //@ResponseBody
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    //@ResponseBody
    public String join(@ModelAttribute User user) {
        log.info("user ={}", user);
        user.setRole("ROLE_USER");

        //userRepository.save(user);
        // 이때 회원가입은 잘되지만 시큐리티로 로그인이 되지 않음
        // 왜?? -> 패스워드 암호화가 되어 있지 않기 때문!!

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    // ADMIN 권한을 가지고 있는 계정만 해당 URL에 접근가능
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    // ADMIN 권한을 가지고 있는 계정만 해당 URL에 접근가능
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "데이터정보";
    }
}
