package com.sparta.zipsa.entity;


import com.sparta.zipsa.dto.ProfileRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String userImage;

    @Column
    private int helpCount;

    @Column
    private int status;

    private int helper;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    @OneToMany(mappedBy = "username" , cascade = CascadeType.REMOVE)
    private List<Board> boards = new ArrayList<>();


    public User(String username, String password, String address, String userImage,UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.address = address;
        this.userImage = userImage;
        this.role = role;
    }

    public void changeRole(UserRoleEnum role) {
        this.role = role;
    }

    public void update(ProfileRequestDto profileRequestDto){
        this.address = profileRequestDto.getAddress();
        this.userImage = profileRequestDto.getUserImage();
    }
}