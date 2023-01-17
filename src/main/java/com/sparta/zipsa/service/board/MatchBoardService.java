package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.dto.StatusRequestDto;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.repository.MatchBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchBoardService {

    private final MatchBoardRepository matchBoardRepository;

    // Match 신청글 작성
    @Transactional
    public MatchBoardResponseDto createMatchBoard(Long boardId, MatchBoardRequestDto requestDto, UserDetails userDetails) {


        // board_id가 있는지 확인
        // Board board = boardRepository.findByid(boardId).orElseThrow(
        //    () -> // 익셉션 출력
        // );

        // 사용자(User)가 있는지 확인
        // User user = userRepository.findByUsername(userDetails.getUsername()).get()


        MatchBoard matchBoard = new MatchBoard(user,requestDto.getContent(),board);
        matchBoardRepository.save(matchBoard);
        return new MatchBoardResponseDto(matchBoard);
    }

    // match 신청글 조회
    @Transactional(readOnly = true)
    public MatchBoardResponseDto getMatchBoard(Long boardId, Long matchBoardId) {
        // board_id가 있는지 확인
        // Board board = boardRepository.findByid(boardId).orElseThrow(
        //    () -> // 익셉션 출력
        // );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 id가 없습니다.")
        );

        return new MatchBoardResponseDto(board,matchBoard);
    }

    // Match 수정
    @Transactional
    public MatchBoardResponseDto updateMatchBoard(Long boardId, Long matchboardId, MatchBoardRequestDto requestDto, UserDetails userDetails) {
        // board_id가 있는지 확인
        // Board board = boardRepository.findByid(boardId).orElseThrow(
        //    () -> // 익셉션 출력
        // );

        MatchBoard matchBoard = matchBoardRepository.findById(matchboardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 id가 없습니다.")
        );

        // 사용자(User)가 있는지 확인
        // User user = userRepository.findByUsername(userDetails.getUsername()).get()


        // 권한 확인
        //if(matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
        //  throw new 익셉션 }

        matchBoard.update(requestDto);

        return new MatchBoardResponseDto(matchBoardRepository.findById(matchBoard.getId()).get());
    }

    public ResponseEntity deleteMatchBoard(Long boardId, Long matchBoardId, UserDetails userDetails) {

        // board_id가 있는지 확인
        // Board board = boardRepository.findByid(boardId).orElseThrow(
        //    () -> // 익셉션 출력
        // );

        // matchBoard id가 있는지 확인
        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 id가 없습니다,")
        );

        // 권한 확인
        // if(matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
        //  throw new 익셉션}

        matchBoardRepository.deleteById(matchBoardId);
        return new ResponseEntity("게시글이 삭제되었습니다.", HttpStatus.OK);

    }

    public MatchBoardResponseDto statusMatchBoard(Long boardId, Long matchBoardId, StatusRequestDto requestDto) {
        // board_id가 있는지 확인
        // Board board = boardRepository.findByid(boardId).orElseThrow(
        //    () -> // 익셉션 출력
        // );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 id가 없습니다.")
        );

    }
}
