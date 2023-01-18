package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MatchBoardResponseDto {

    private Long id;
    private int help_cnt;
    private String content;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Long board_id;

    private int status;

    // 추후 수정 예정
    private String profile_img;


    public MatchBoardResponseDto(MatchBoard matchBoard, Board board) {
        this.id = matchBoard.getId();
        matchBoard.addhelpCount();
        this.content = matchBoard.getContent();
        this.username = matchBoard.getUsername();
        this.createdAt = matchBoard.getCreatedAt();
        this.modifiedAt = matchBoard.getModifiedAt();
        this.status = matchBoard.getstatusList();
        this.board_id = board.getId();
        this.profile_img = getProfile_img();
    }


}
