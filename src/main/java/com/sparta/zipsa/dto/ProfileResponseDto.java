package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.User;

public class ProfileResponseDto {

    User user;

    public ProfileResponseDto(User user){
        this.user = user;
    }
}
