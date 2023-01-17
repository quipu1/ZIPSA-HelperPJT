package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequestDto;
import com.sparta.zipsa.dto.BoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import com.sparta.zipsa.repository.*;
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private static UserRepository userRepository;
    private static BoardRepository boardRepository;
    @Override
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequest, User user)
    {
        Board board = new Board(boardRequest,user);
        boardRepository.save(board);
        BoardResponseDto boardResponse = new BoardResponseDto(board);
        return boardResponse;
    }
    @Override
    @Transactional
    public BoardResponseDto revisionBoard(Long boardId, BoardRequestDto boardRequest, User user)
    {
        Board board =boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        board.changePost(user, boardRequest.getTitle(), boardRequest.getContents());
        boardRepository.save(board);
        BoardResponseDto boardResponse = new BoardResponseDto(board);
        return boardResponse;
    }

    @Override
    @Transactional
    public String deleteBoard(Long boardId, User user)
    {
        Board board =boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        boardRepository.delete(board);

        String deletePrint = Long.toString(boardId);
        String print = "게시글이 삭제되었습니다.";
        deletePrint.concat(print);
        return deletePrint;
    }
}
