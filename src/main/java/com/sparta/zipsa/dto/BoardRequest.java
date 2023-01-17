package com.sparta.zipsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private String contents;
    private Long price;

    public BoardRequest(String title, String contents, Long price) {
        this.title = title;
        this.contents = contents;
        this.price = price;
    }
}
