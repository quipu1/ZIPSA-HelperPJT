package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;


import java.time.LocalDateTime;

public class HelperBoardResponseDto {

    private Long id;
    private String username;

    private String address;

    private String userImage;

    private int helper;

    private int helpCount;

    private String message;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public HelperBoardResponseDto(HelperBoard helperBoard, User user) {
        this.id = helperBoard.getId();
        this.username = helperBoard.getUsername();
        this.address = user.getAddress();
        this.userImage = user.getUserImage();
        this.helper = user.getHelper();
        this.helpCount = user.getHelpCount();
        this.message = helperBoard.getMessage();
        this.createdAt = helperBoard.getCreatedAt();
        this.modifiedAt = helperBoard.getModifiedAt();

    }
}







