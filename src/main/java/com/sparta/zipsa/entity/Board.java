package com.sparta.zipsa.entity;

import com.sparta.zipsa.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = true)
    private String modifiedAt;

    @Column(nullable = false)
    private String status = "모집중";

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(fetch =FetchType.LAZY,mappedBy = "memo",cascade = CascadeType.ALL)
    private List<String> contents = new ArrayList<>();;
    @Column(nullable = false)
    private Long price;

    public Board(BoardRequestDto boardRequest, User user) {
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.title = boardRequest.getTitle();
        this.contents = boardRequest.getContents();
        this.price = boardRequest.getPrice();
        this.user = user;
    }

    public void changeStatus(String status)
    {
        this.status = status;
    }

    public void changeBoard(User user, String title, List<String> contents) {
    }

    public void boardAddMatchedBoard(List<Board> board)
    {

    }
}
