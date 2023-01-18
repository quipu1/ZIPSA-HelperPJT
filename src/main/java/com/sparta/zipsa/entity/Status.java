package com.sparta.zipsa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Status {
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
