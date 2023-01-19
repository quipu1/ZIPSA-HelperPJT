package com.sparta.zipsa.entity;

import com.sparta.zipsa.dto.HelperBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class HelperBoard extends TimeStamp {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String userImage;

    @Column(nullable = false)
    private int helper;

    @Column(nullable = false)
    private int helpCount;

    @Column(nullable = false)
    private String message;

    public HelperBoard(HelperBoardRequestDto helperBoardRequestDto, User user) {

        this.username = user.getUsername();
        this.address = user.getAddress();
        this.userImage = user.getUserImage();
        this.helper = user.getHelper();
        this.helpCount = user.getHelpCount();
        this.message = helperBoardRequestDto.getMessage();

    }

    public void update(HelperBoardRequestDto helperBoardRequestDto) {
        this.message = helperBoardRequestDto.getMessage();
    }


}

