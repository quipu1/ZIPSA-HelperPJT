package com.sparta.zipsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HelperBoardRequestDto {
    private String content;

    public HelperBoardRequestDto(String content) {
        this.content = content;
    }
}
