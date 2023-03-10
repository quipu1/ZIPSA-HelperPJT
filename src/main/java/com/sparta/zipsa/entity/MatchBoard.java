package com.sparta.zipsa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.zipsa.dto.MatchBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MatchBoard extends TimeStamp{

    // 글 고유 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    @Column(nullable = false)
    public String content;

    @Column(nullable = false)
    public String username;

    @Column(nullable = false)
    public String status = "신청중";

    @Column(nullable = false)
    public int helpCnt;

    @Column(nullable = false)
    public String userImg;

    //@JsonBackReference
    // 한개의 게시글에 여러개의 신청을 할 수 있다..?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_Id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    public MatchBoard(User user, Board board, MatchBoardRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.username = user.getUsername();
        this.helpCnt = user.getHelpCnt();
        this.userImg = user.getUserImage();
        this.board = board;
        this.user = user;
    }

    public MatchBoard( String content, String username, Board board, String userImg) {
        this.content = content;
        this.username = username;
        this.board = board;
        this.userImg = userImg;
    }

    public void upStatus() {
        this.status = "수락😊";
    }

    public void downStatus() {
        this.status = "거절😢";
    }

    public void update(MatchBoardRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}


