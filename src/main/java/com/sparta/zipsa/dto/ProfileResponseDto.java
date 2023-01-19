package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {

    User user;

    public ProfileResponseDto(User user){
        this.user = user;
    }
}
