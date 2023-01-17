package com.sparta.zipsa.controller;

import com.sparta.zipsa.dto.BoardRequest;
import com.sparta.zipsa.dto.BoardResponse;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.board.BoardService;
import com.sparta.zipsa.service.board.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
@PreAuthorize("hasRole('ROLE_HELPER') or hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")

public class  BoardController {
    private final BoardService boardService;
    @PostMapping
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest, @AuthenticationPrincipal User user)
    {
        boardService.createBoard(boardRequest,user);
    }

}
