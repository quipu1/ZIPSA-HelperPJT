package com.sparta.zipsa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Status {

    // 심부름 수락 및 거절 기능을 만들기 위한 엔티티
    // 좋아요 기능을 따라 구현해서 나중에 수정을 무조건 해야할 것 같아요
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Status_ID")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCHBOARD_ID")
    private MatchBoard matchBoard;

    public Status(Board board, MatchBoard matchBoard) {
        this.board = board;
        this.matchBoard = matchBoard;
    }
}
