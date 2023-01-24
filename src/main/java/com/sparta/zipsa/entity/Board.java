package com.sparta.zipsa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.zipsa.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Board extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String status = "모집중";

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @JsonManagedReference
    @Column(nullable = false)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<MatchBoard> matchBoard = new ArrayList<>();


    public Board(BoardRequestDto boardRequest, User user) {
        this.username = user.getUsername();
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
        this.address = user.getAddress();
        this.price = boardRequest.getPrice();
        this.user = user;
    }

    public void changeStatus()
    {
        this.status = "완료";
    }

    public void editBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board( String username, String title, String content, String address, Long price, String status, User user){
        this.user = user;
        this.username = username;
        this.title = title;
        this.content = content;
        this.address = address;
        this.price = price;
        this.status = status;
    }

}
