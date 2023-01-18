package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String username;

    private String password;

    private UserRoleEnum role;

    public LoginResponseDto(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}