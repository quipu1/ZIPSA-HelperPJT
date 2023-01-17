package com.sparta.zipsa.entity;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MatchBoard extends TimeStamped{

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
    public String status;

    // 한개의 게시글에 여러개의 신청을 할 수 있다..?
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    public void update(MatchBoardRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
