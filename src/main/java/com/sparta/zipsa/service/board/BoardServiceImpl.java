package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequestDto;
import com.sparta.zipsa.dto.BoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.BoardException;
import com.sparta.zipsa.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.zipsa.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    //게시글 생성
    @Override
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequest, User user) {
        Board board = new Board(boardRequest,user);
        boardRepository.save(board);
        BoardResponseDto boardResponse = new BoardResponseDto(board);
        return boardResponse;
    }


    //모든 게시글 조회 - 페이징
    @Override
    @Transactional
    public Page<BoardResponseDto> getBoardAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boards = boardRepository.findAll(pageable);
        Page<BoardResponseDto> boardListDto = boards.map(BoardResponseDto::toBoardResponseDto);

        return boardListDto;
    }


    //작성자의 내가 쓴 글 모두 조회 - 페이징
    @Override
    @Transactional
    public Page<BoardResponseDto> getMyBoardAll(int page, int size, Long userId, User user) {

        if (userId != user.getId()) {
            throw new UserException.AuthorityException();
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> myBoardList = boardRepository.findAllByUsername(pageable, user.getUsername());
        Page<BoardResponseDto> myBoardListDto = myBoardList.map(BoardResponseDto::toBoardResponseDto);

        return  myBoardListDto;
    }


    //선택한 게시글 조회
    @Override
    @Transactional
    public BoardResponseDto getBoard(Long boardId, String username) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardException.BoardNotFoundException::new);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        return boardResponseDto;
    }


    //선택한 게시글 수정
    @Override
    @Transactional
    public BoardResponseDto revisionBoard(Long boardId, BoardRequestDto boardRequest, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardException.BoardNotFoundException::new);
        checkUser(user, board);
        board.editBoard(boardRequest.getTitle(), boardRequest.getContent());
        boardRepository.save(board);
        BoardResponseDto boardResponse = new BoardResponseDto(board);
        return boardResponse;
    }


    @Override
    @Transactional
    public ResponseEntity deleteBoard(Long boardId, User user)
    {
        Board board =boardRepository.findById(boardId).orElseThrow(BoardException.BoardNotFoundException::new);
        checkUser(user, board);
        boardRepository.deleteById(boardId);

        StringBuffer deletePrint = new StringBuffer();
        String boardIdChar = Long.toString(boardId);
        String print = "번 게시글이 삭제되었습니다.";
        deletePrint.append(boardIdChar);
        deletePrint.append(print);


        return new ResponseEntity<>(deletePrint, HttpStatus.OK);
    }


    //작성자와 현재 유저가 같은 지, ADMIN인지 확인
    private void checkUser(User user, Board board) {
        if (user.getRole() != UserRoleEnum.ADMIN  && !user.getUsername().equals(board.getUsername())) {
            throw new UserException.AuthorityException();
        }
    }


}
