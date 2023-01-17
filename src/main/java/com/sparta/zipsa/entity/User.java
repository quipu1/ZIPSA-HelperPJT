package com.sparta.zipsa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//username, password, address, user_img, helper,role,help_cnt
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String address;

    @Column(columnDefinition = "TEXT")
    private String user_img;

    @Column
    int help_cnt;

    @Column
    @Enumerated
    private UserRoleEnum role;

    public User(String username, String password, String address, String user_img,UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.address = address;
        this.user_img = user_img;
        this.role = role;
    }
}