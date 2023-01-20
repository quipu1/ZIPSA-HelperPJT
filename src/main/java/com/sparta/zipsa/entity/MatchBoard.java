package com.sparta.zipsa.entity;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    // 한개의 게시글에 여러개의 신청을 할 수 있다..?
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "board_Id")
    private Board board;

    public MatchBoard(User user, MatchBoardRequestDto requestDto) {
        this.username = user.getUsername();
        this.content = requestDto.getContent();
        this.helpCnt = user.getHelpCnt();
        this.userImg = user.getUserImage();
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


