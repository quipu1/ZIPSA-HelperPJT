package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;
    private String userImage;
    private String address;
    private UserRoleEnum role;


    public UserResponseDto(User user){

        this.username = user.getUsername();
        this.userImage = user.getUserImage();
        this.address = user.getAddress();
        this.role = user.getRole();
    }
    }

