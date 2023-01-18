package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.entity.Status;
import com.sparta.zipsa.repository.BoardRepository;
import com.sparta.zipsa.repository.HelpStatusRepository;
import com.sparta.zipsa.repository.MatchBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchBoardServiceImpl implements MatchBoardService {

    private final MatchBoardRepository matchBoardRepository;
    private final BoardRepository boardRepository;
    private final HelpStatusRepository helpStatusRepository;

   // MatchBoard 생성
    @Override
    public MatchBoardResponseDto createMatchBoard(Long boardId, MatchBoardRequestDto requestDto, UserDetails userDetails) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다.")
        );

        // 유저 화인
        // User user = userRepository.findByUsername().orElseThrow(
        //  // () -> new 익셉션
        // );

        MatchBoard matchBoard = new MatchBoard(user,requestDto.getContent(),board);
        matchBoardRepository.save(matchBoard);
        return new MatchBoardResponseDto(matchBoard,board);
    }

    // MatchBoard 조회
    @Override
    public MatchBoardResponseDto getMatchBoard(Long boardId, Long matchBoardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다.")
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다.")
        );

        return new MatchBoardResponseDto(matchBoard,board);
    }

    // MatchBoard 수정
    @Override
    public MatchBoardResponseDto updateMatchBoard(Long boardId, Long matchBoardId, MatchBoardRequestDto requestDto, UserDetails userDetails) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다.")
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다.")
        );

        if (matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        matchBoard.update(requestDto);
        return new MatchBoardResponseDto(matchBoardRepository.save(matchBoard),board);
    }

    // MatchBoard 삭제
    @Override
    public ResponseEntity deleteMatchBoard(Long boardId, Long matchBoardId, UserDetails userDetails) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다.")
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 MatchBoard Id가 없습니다.")
        );

        if (matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        matchBoardRepository.deleteById(matchBoardId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    // 상태 및 도움 횟수 추가(수락) 기능
    // 도움 횟수는 수락 시 올라가지만 거절 할 때에는 내려가지 않는다.
    @Override
    public boolean addStatus(Long boardId, Long matchboardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다")
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchboardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 MatchBoard Id가 없습니다.")
        );
        List<Status> addStatus= helpStatusRepository.findByUserAndMatchBoard(board,matchBoard);
        if (addStatus.isEmpty()) {
            helpStatusRepository.save(new Status(board,matchBoard));
            matchBoard.addhelpCount();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteStatus(Long boardId, Long matchboardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Board Id가 없습니다.")
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchboardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 MatchBoard Id가 없습니다.")
        );

        List<Status> addStatus = helpStatusRepository.findByUserAndMatchBoard(board,matchBoard);
        if (!addStatus.isEmpty()) {
            helpStatusRepository.delete(addStatus.get(0));
            return true;
        }
        return false;
    }


}
