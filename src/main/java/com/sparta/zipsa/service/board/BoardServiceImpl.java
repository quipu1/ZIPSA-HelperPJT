package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequestDto;
import com.sparta.zipsa.dto.BoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.sparta.zipsa.repository.*;
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private static UserRepository userRepository;
    private static BoardRepository boardRepository;
    @Override
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequest, @AuthenticationPrincipal User user)
    {

        Board board = new Board(boardRequest,user);
        boardRepository.save(board);

        BoardResponseDto boardResponse = new BoardResponseDto(board);
        return boardResponse;
    }
}
