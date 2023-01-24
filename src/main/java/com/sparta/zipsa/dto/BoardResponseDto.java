package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
        private Long id;
        private String title;
        private String content;
        private String username;
        private String address;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Long price;
        private String status;
        private List<MatchBoardResponseDto> matchBoards = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUsername();
        this.address = board.getAddress();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.price = board.getPrice();
        this.status = board.getStatus();
        this.matchBoards = board.getMatchBoard()
                .stream()
                .map(MatchBoardResponseDto::new)
                .collect(Collectors.toList());
    }

}
