package com.sparta.zipsa.controller;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.matchBoard.MatchBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchBoardController {

    private final MatchBoardService matchBoardService;

    // Match 신청글 작성
    @PostMapping("/api/board/{board_id}/matchboard")
    public MatchBoardResponseDto createMatchBoard(@PathVariable Long board_id, @RequestBody MatchBoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return matchBoardService.createMatchBoard(board_id,requestDto,userDetails);
    }

    // Match 신청글 전체 조회 (페이징 처리)
    @GetMapping("/api/board/matchboard")
    @ResponseStatus(HttpStatus.OK)
    public Page<MatchBoard> getAllMatchBoard(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc,
            @RequestParam("role") String role
    ) {
        return matchBoardService.getAllMatchBoard(page,size,isAsc,role);
    }


    // Match 신청글 선택 조회
    @GetMapping("/api/board/{board_id}/matchboard/{matchboard_id}")
    public MatchBoardResponseDto getMatchBoard(@PathVariable Long board_id, @PathVariable Long matchboard_id) {
        return matchBoardService.getMatchBoard(board_id,matchboard_id);
    }

    // Match 신청글 수정
    @PostMapping("/api/board/{board_id}/matchboard/{matchboard_id}")
    public MatchBoardResponseDto updateMatchBoard(@PathVariable Long board_id, @PathVariable Long matchboard_id, @RequestBody MatchBoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return matchBoardService.updateMatchBoard(board_id,matchboard_id,requestDto,userDetails.getUser());
    }

    // Match 신청글 삭제
    @DeleteMapping("/api/board/{board_id}/mathchboard/{matchBoard_id}")
    public ResponseEntity deleteMatchBoard(@PathVariable Long board_id, @PathVariable Long matchBoard_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return matchBoardService.deleteMatchBoard(board_id,matchBoard_id,userDetails.getUser());
    }

    // 심부름 신청글 수럭
    @PostMapping("/api/board/{board_id}/matchboard/{matchboard_id}/up")
    public ResponseEntity upStatus(@PathVariable Long board_id, @PathVariable Long matchboard_id) {
        return matchBoardService.upStatus(board_id,matchboard_id);
    }

    // 심부름 신청글 거절
    @PostMapping("/api/board/{board_id}/matchboard/{matchboard_id}/down")
    public ResponseEntity downStatus(@PathVariable Long board_id, @PathVariable Long matchboard_id) {
        return matchBoardService.downStatus(board_id,matchboard_id);
    }

}
