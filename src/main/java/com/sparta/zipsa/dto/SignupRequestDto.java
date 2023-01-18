package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import javax.validation.constrains.Pattern;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
    String username;

    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])^[a-zA-Z0-9~!@#$%^&*()+|=]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자(~!@#$%^&*()+|=)")
    String password;

    private boolean admin = false;

    private String adminToken = "";

    private String address;

    private String user_img;
}

