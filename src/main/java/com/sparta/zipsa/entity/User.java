package com.sparta.zipsa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User extends TimeStamp {

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = true)
    private String status;
    private String address;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, UserRoleEnum userRoleEnum) {
        this.username = username;
        this.userRoleEnum = userRoleEnum;
    }
}
