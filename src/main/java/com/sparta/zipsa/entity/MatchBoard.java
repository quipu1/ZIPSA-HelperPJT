package com.sparta.zipsa.entity;

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
    public int help_cnt;

    @Column(nullable = false)
    public String status = "모집중";

    // 한개의 게시글에 여러개의 신청을 할 수 있다..?
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "board_Id")
    private Board board;

    public MatchBoard(User user, MatchBoardRequestDto requestDto, Board board) {
        this.username = user.getUsername();
        this.content = requestDto.getContent();
        this.board = board;
    }

    // @ColumnDefault 어노테이션 사용으로 기본 값 0부터 시작
    // ServiceImpl에서 수락을 할 시 해당 카운트가 올라가도록 해놨는데 잘 될지 모르겠어요
    @ColumnDefault("0")
    public void addhelpCount() {
        help_cnt += 1;
    }

    public void upStatus() {
        this.status = "수락된 게시물";
    }

    public void downStatus() {
        this.status = "거절된 게시물";
    }

    public void update(MatchBoardRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}


