package com.sparta.zipsa.controller;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.service.board.MatchBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchBoardController {

    private final MatchBoardService matchBoardService;

    // Match 신청글 작성
    @PostMapping("/api/board/{board_id}/matchboard")
    public MatchBoardResponseDto createMatchBoard(@PathVariable Long board_id, @RequestBody MatchBoardRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return matchBoardService.createMatchBoard(board_id,requestDto,userDetails);
    }

    // Match 신청글 조회
    @GetMapping("/api/board/{board_id}/matchboard/{matchboard_id}")
    public MatchBoardResponseDto getMatchBoard(@PathVariable Long board_id, @PathVariable Long matchboard_id) {
        return matchBoardService.getMatchBoard(board_id,matchboard_id);
    }

    // Match 신청글 수정
    @PostMapping("/api/board/{board_id}/matchboard/{matchboard_id}")
    public MatchBoardResponseDto updateMatchBoard(@PathVariable Long board_id, @PathVariable Long matchboard_id, @RequestBody MatchBoardRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return matchBoardService.updateMatchBoard(board_id,matchboard_id,requestDto,userDetails);
    }

    // Match 신청글 삭제
    @DeleteMapping("/api/board/{board_id}/mathchboard/{matchBoard_id}")
    public ResponseEntity deleteMatchBoard(@PathVariable Long board_id, @PathVariable Long matchBoard_id, @AuthenticationPrincipal UserDetails userDetails){
        return matchBoardService.deleteMatchBoard(board_id,matchBoard_id,userDetails);
    }

    // 심부름 신청글 수락
    @PostMapping("/api/board/{board_id}/matchboard/{matchboard_id}")
    public ResponseEntity addStatus(@PathVariable Long board_id, @PathVariable Long matchboard_id){
        boolean statusCheck = matchBoardService.addStatus(board_id,matchboard_id);
        if (statusCheck) {
            return new ResponseEntity<>("수락이 완료되었습니다", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미 수락이 된 게시물입니다.", HttpStatus.OK);
        }
    }

    // 심부름 신청글 거절
    @DeleteMapping("/api/board/{board_id}/matchboard/{matchboard_id}")
    public ResponseEntity deleteStatus(@PathVariable Long board_id, @PathVariable Long matchboard_id) {
        boolean unStatusCheck = matchBoardService.deleteStatus(board_id,matchboard_id);
        if (unStatusCheck) {
            return new ResponseEntity<>("거절이 완료되었습니다.",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미 거절하셨거나, 거절 된 게시물입니다.",HttpStatus.OK);
        }
    }

}
