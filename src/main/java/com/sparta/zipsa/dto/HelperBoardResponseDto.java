package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class HelperBoardResponseDto {

    private Long id;
    private String username;

    private String address;

    private String userImage;

    private int helpCount;

    private String content;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public HelperBoardResponseDto(HelperBoard helperBoard) {
        this.id = helperBoard.getId();
        this.username = helperBoard.getUsername();
        this.address = helperBoard.getAddress();
        this.userImage = helperBoard.getUserImage();
        this.helpCount = helperBoard.getHelpCount();
        this.content = helperBoard.getContent();
        this.createdAt = helperBoard.getCreatedAt();
        this.modifiedAt = helperBoard.getModifiedAt();
    }

    public HelperBoardResponseDto(Optional<HelperBoard> helperBoard) {
        this.id = helperBoard.get().getId();
        this.username = helperBoard.get().getUsername();
        this.address = helperBoard.get().getAddress();
        this.userImage = helperBoard.get().getUserImage();
        this.helpCount = helperBoard.get().getHelpCount();
        this.content = helperBoard.get().getContent();
        this.createdAt = helperBoard.get().getCreatedAt();
        this.modifiedAt = helperBoard.get().getModifiedAt();

    }
}







