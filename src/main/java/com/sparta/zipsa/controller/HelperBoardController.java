package com.sparta.zipsa.controller;

import com.sparta.zipsa.dto.HelperBoardRequestDto;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.helperBoard.HelperBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HelperBoardController {

    private final HelperBoardService helperBoardService;

    //집사 신청글 작성
    @PostMapping("/api/helperBoard")
    public ResponseEntity createHelperBoard(@RequestBody HelperBoardRequestDto helperBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.createHelperBoard(helperBoardRequestDto, userDetails.getUser());
    }
    //집사 신청글 삭제
    @DeleteMapping("/api/helperBoard/{helperBoardId}")
    public ResponseEntity deleteHelperBoard(@PathVariable Long helperBoardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.deleteHelperBoard(helperBoardId, userDetails.getUser());
    }
    //집사 신청글 수정
    @PutMapping("/api/helperBoard/{helperBoardId}")
    public ResponseEntity updateHelperBoard(@PathVariable Long helperBoardId, @RequestBody HelperBoardRequestDto helperBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.updateHelperBoard(helperBoardId, helperBoardRequestDto, userDetails.getUser());
    }
}
