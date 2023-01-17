package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequest;
import com.sparta.zipsa.dto.BoardResponse;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
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
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest, @AuthenticationPrincipal User user)
    {
        Board board = new Board(boardRequest,user);
        boardRepository.save(board);
        BoardResponse boardResponse = new BoardResponse(board);
        return boardResponse;
    }
}
