package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MatchBoardResponseDto {

    private Long id;
    private int helpCnt;
    private String content;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Long boardId;

    private String status;

    // 추후 수정 예정
    private String userImg;


    public MatchBoardResponseDto(MatchBoard matchBoard, Board board) {
        this.id = matchBoard.getId();
        this.helpCnt = matchBoard.getHelpCnt();
        this.content = matchBoard.getContent();
        this.username = matchBoard.getUsername();
        this.createdAt = matchBoard.getCreatedAt();
        this.modifiedAt = matchBoard.getModifiedAt();
        this.status = matchBoard.getStatus();
        this.boardId = board.getId();
        this.userImg = matchBoard.getUserImg();
    }


    public static MatchBoardResponseDto toMatchBoardResponseDto(final MatchBoard matchBoard) {
        return MatchBoardResponseDto.builder()
                .id(matchBoard.getId())
                .username(matchBoard.getUsername())
                .content(matchBoard.getContent())
                .createdAt(matchBoard.getCreatedAt())
                .modifiedAt(matchBoard.getModifiedAt())
                .status(matchBoard.getStatus())
                .boardId(matchBoard.getId())
                .helpCnt(matchBoard.getHelpCnt())
                .userImg(matchBoard.getUserImg())
                .build();
    }
}
