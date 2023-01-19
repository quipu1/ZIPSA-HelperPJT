package com.sparta.zipsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HelperBoardRequestDto {
    private String message;

    public HelperBoardRequestDto(String message) {
        this.message = message;
    }
}
