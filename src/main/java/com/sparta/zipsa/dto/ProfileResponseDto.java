package com.sparta.zipsa.dto;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {

    private String username;
    private String address;
    private String userImage;
    private int helpCnt;
    private UserRoleEnum role;

    public ProfileResponseDto(User user){
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.userImage = user.getUserImage();
        this.helpCnt = user.getHelpCnt();
        this.role = user.getRole();
    }
}
