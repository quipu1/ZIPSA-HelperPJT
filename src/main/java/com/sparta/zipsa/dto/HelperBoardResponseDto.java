package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HelperBoardResponseDto {

    private Long id;
    private String username;

    private String address;

    private String userImage;

    private int helpCnt;

    private String content;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public HelperBoardResponseDto(HelperBoard helperBoard, User user) {
        this.id = helperBoard.getId();
        this.username = helperBoard.getUsername();
        this.address = user.getAddress();
        this.userImage = user.getUserImage();
        this.helpCount = user.getHelpCnt();
        this.content = helperBoard.getContent();
        this.createdAt = helperBoard.getCreatedAt();
        this.modifiedAt = helperBoard.getModifiedAt();

    }
}







