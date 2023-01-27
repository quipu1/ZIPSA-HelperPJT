package com.sparta.zipsa.controller;

import com.sparta.zipsa.dto.HelperBoardRequestDto;
import com.sparta.zipsa.dto.HelperBoardResponseDto;
import com.sparta.zipsa.dto.HelperBoardSearchCond;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.helperBoard.HelperBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HelperBoardController {

    private final HelperBoardService helperBoardService;

    //헬퍼 신청글 작성
    @PostMapping("helperBoard")
    public ResponseEntity createHelperBoard(@RequestBody HelperBoardRequestDto helperBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.createHelperBoard(helperBoardRequestDto, userDetails.getUser());
    }

    //헬퍼 신청글 삭제
    @DeleteMapping("helperBoard/{helperBoardId}")
    public ResponseEntity deleteHelperBoard(@PathVariable Long helperBoardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.deleteHelperBoard(helperBoardId, userDetails.getUser());
    }

    //헬퍼 신청글 수정
    @PutMapping("helperBoard/{helperBoardId}")
    public ResponseEntity updateHelperBoard(@PathVariable Long helperBoardId, @RequestBody HelperBoardRequestDto helperBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.updateHelperBoard(helperBoardId, helperBoardRequestDto, userDetails.getUser());
    }

    //헬퍼 신청글 조회
    @GetMapping("helperBoard")
    public HelperBoardResponseDto getHelperBoard(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.getHelperBoard(userDetails.getUser());
    }

    //헬퍼 신청글 전체 조회 (admin)
    @GetMapping("helperBoard/admin")
    public List<HelperBoardResponseDto> getHelperBoardList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HelperBoardSearchCond searchCond
    ) {
        return helperBoardService.getHelperBoardList(userDetails.getUser(), searchCond);
    }

}
