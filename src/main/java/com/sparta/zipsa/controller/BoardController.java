package com.sparta.zipsa.controller;

import com.sparta.zipsa.dto.BoardRequestDto;
import com.sparta.zipsa.dto.BoardResponseDto;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_HELPER') or hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")

public class BoardController {
    private final BoardService boardService;

    //게시글 생성
    @PostMapping("/board")
    @ResponseStatus(HttpStatus.OK)
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequest, userDetails.getUser());
    }

    //모든 게시글 조회
    @GetMapping("/boards")
    @ResponseStatus(HttpStatus.OK)
    public Page<BoardResponseDto> getBoardAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return boardService.getBoardAll(page, size);
    }

    //작성자의 내가 쓴 글 모두 조회
    @GetMapping("/myboard/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<BoardResponseDto> getMyBoardAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getMyBoardAll(page, size, userId, userDetails.getUser());
    }

    //선택한 게시글 조회
    @GetMapping("/board/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardResponseDto getBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoard(boardId, userDetails.getUsername());
    }

    //선택한 게시글 수정
    @PatchMapping("/board/{boardId}")
    public BoardResponseDto revisionBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequest, @AuthenticationPrincipal User user) {
        return boardService.revisionBoard(boardId, boardRequest, user);
    }

    //선택한 게시글 삭제
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal User user) {
        return boardService.deleteBoard(boardId, user);
    }



}
