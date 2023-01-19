package com.sparta.zipsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;
    private Long price;

    public BoardRequestDto(String title, String content, Long price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
