package com.sparta.zipsa.controller;

import com.sparta.zipsa.dto.HelperBoardRequestDto;
import com.sparta.zipsa.dto.HelperBoardResponseDto;
import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.board.HelperBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HelperBoardController {

    private HelperBoardService helperBoardService;

    @PostMapping("/api/helperBoard")
    public ResponseEntity createHelperBoard(@RequestBody HelperBoardRequestDto helperBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.createHelperBoard(helperBoardRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/helperBoard/{helperBoardId}")
    public ResponseEntity deleteHelperBoard(@PathVariable Long helperBoardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.deleteHelperBoard(helperBoardId, userDetails.getUser());
    }

    @PutMapping("/api/helperBoard/{helperBoardId}")
    public ResponseEntity updateHelperBoard(@PathVariable Long helperBoardId, @RequestBody HelperBoardRequestDto helperBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return helperBoardService.updateHelperBoard(helperBoardId, helperBoardRequestDto, userDetails.getUser());
    }
}
