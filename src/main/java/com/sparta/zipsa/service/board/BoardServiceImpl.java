package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequestDto;
import com.sparta.zipsa.dto.BoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import com.sparta.zipsa.repository.*;

import java.util.ArrayList;
import java.util.List;

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
        board.changeBoard(user, boardRequest.getTitle(), boardRequest.getContents());
        boardRepository.save(board);
        BoardResponseDto boardResponse = new BoardResponseDto(board);
        return boardResponse;
    }

    @Override
    @Transactional
    public ResponseEntity deleteBoard(Long boardId, User user)
    {
        Board board =boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        boardRepository.deleteById(boardId);

        String deletePrint = Long.toString(boardId);
        String print = "게시글이 삭제되었습니다.";
        deletePrint.concat(print);


        return new ResponseEntity<>(deletePrint, HttpStatus.OK);;
    }

    @Override
    @Transactional
    public List<BoardResponseDto> getBoardAll() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> resultBoards = new ArrayList<>();

        for(Board board : boards)
        {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            resultBoards.add(boardResponseDto);
        }

        return resultBoards;
    }

    @Override
    @Transactional
    public List<BoardResponseDto> getUserBoardAll(String userName) {
        List<Board> boards = boardRepository.findBoardsByUsername(userName);
        List<BoardResponseDto> resultBoards = new ArrayList<>();

        for(Board board : boards)
        {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            resultBoards.add(boardResponseDto);
        }
        return resultBoards;
    }

}
