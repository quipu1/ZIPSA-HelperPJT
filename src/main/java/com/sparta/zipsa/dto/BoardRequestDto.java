package com.sparta.zipsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private List<String> contents;
    private Long price;

    public BoardRequestDto(String title, List<String> contents, Long price) {
        this.title = title;
        this.contents = contents;
        this.price = price;
    }
}
