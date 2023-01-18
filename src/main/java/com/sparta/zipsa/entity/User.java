package com.sparta.zipsa.entity;


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

    @Column/*(columnDefinition = "TEXT")*/
    private String userImage;

    @Column
    int helpCount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    @OneToMany(mappedBy = "username" , cascade = CascadeType.REMOVE)
    private List<Board> boards = new ArrayList<>();

//혹시 쓰실거면 쓰세요 일단 주석처리! 연관관계메소드
//    public void addPost(Board board){
//        board.setUsers(this);
//        this.boards.add(board);
//    }




    public User(String username, String password, String address, String userImage,UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.address = address;
        this.userImage = userImage;
        this.role = role;
    }
}