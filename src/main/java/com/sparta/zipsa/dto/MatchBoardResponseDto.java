package com.sparta.zipsa.dto;

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

    private String status;

    private String profile_img;


    public MatchBoardResponseDto(MatchBoard matchBoard) {
        this.id = matchBoard.getId();
        this.help_cnt = matchBoard.getHelp_cnt();
        this.content = matchBoard.getContent();
        this.username = matchBoard.getUsername();
        this.createdAt = matchBoard.getCreateAt();
        this.modifiedAt = matchBoard.getModifiedAt();
        this.status = matchBoard.getStatus();
    }

    //    public MatchBoardResponseDto(MatchBoard matchBoard,Board board) {
    //        this.id = matchBoard.getId();
    //        this.help_cnt = matchBoard.getHelp_cnt();
    //        this.content = matchBoard.getContent();
    //        this.username = matchBoard.getUsername();
    //        this.createdAt = matchBoard.getCreateAt();
    //        this.modifiedAt = matchBoard.getModifiedAt();
    //        this.status = matchBoard.getStatus();
    //        this.profile_img = getProfile_img();
    //    }

    // 여기에 BoardId도 해야하는데.. 어떻게 해야하지..
}
