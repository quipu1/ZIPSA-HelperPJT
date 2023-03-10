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

    @Column(nullable = true)
    private int helpCnt = 0;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> boards = new ArrayList<>();

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<MatchBoard> matchBoards = new ArrayList<>();


    public User(String username, String password, String address, String userImage, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.userImage = userImage;
        this.role = role;
    }

    public void changeRole(UserRoleEnum role) {
        this.role = role;
    }

    public void addHelpCnt() {
        this.helpCnt += 1;
    }

    public void update(ProfileRequestDto profileRequestDto, String filename) {
        this.address = profileRequestDto.getAddress();
        this.userImage = filename;
    }

    public void updateNotImage(ProfileRequestDto profileRequestDto) {
        this.address = profileRequestDto.getAddress();
    }

    public void modifyPassword(String password){
        this.password = password;
    }
}