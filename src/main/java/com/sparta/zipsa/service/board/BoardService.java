package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequestDto;
import com.sparta.zipsa.dto.BoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BoardService {
    BoardResponseDto createBoard(BoardRequestDto boardRequest, User user);

    List<BoardResponseDto> getBoardAll(int page, int size);

    List<BoardResponseDto> getMyBoardAll(int page, int size, Long userId, User user);

    Board getBoard(Long boardId);

    BoardResponseDto revisionBoard(Long boardId, BoardRequestDto boardRequest, User user);

    ResponseEntity deleteBoard(Long boardId, User user);

}
