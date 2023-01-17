package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardResponseDto {
        private Long id;
        private String title;
        private String contents;
        private String username;
        private String address;
        private String createdAt;
        private String modifiedAt;
        private Long price;
        private String status;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.address = board.getAddress();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.price = board.getPrice();
        this.status = board.getStatus();
    }
}
