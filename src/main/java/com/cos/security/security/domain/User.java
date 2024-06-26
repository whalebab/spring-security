package com.cos.security.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String email;
    private String role;        // ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    private Timestamp createDate;
}
