package com.sparta.zipsa.dto;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class HelperBoardSearchCond {
    @Getter
    private String search;
    private int page;
    private int size;
    private boolean isASc;

    public Pageable toPageable() {
        page -= 1; // JPA 는 페이징 0부터 시작
        page = Math.max(page, 0);
        Sort.Direction direction = isASc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(page, size, sort);
    }
}
