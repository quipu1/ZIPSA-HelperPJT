package com.sparta.zipsa.service.matchBoard;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface MatchBoardService {

    // MatchBoard 생성
    MatchBoardResponseDto createMatchBoard(Long boardId, MatchBoardRequestDto requestDto, UserDetailsImpl userDetails);

    // MatchBoard 선택 조회
    MatchBoardResponseDto getMatchBoard(Long boardId, Long matchBoardId);

    // MatchBoard 수정
    MatchBoardResponseDto updateMatchBoard(Long boardId, Long matchBoardId, MatchBoardRequestDto requestDto, User user);

    // MatchBoard 삭제
    ResponseEntity deleteMatchBoard(Long boardId, Long matchBoardId,User user);

    // MatchBoard 전체 조회 (페이징 처리)
    Page<MatchBoardResponseDto> getAllMatchBoard(int page, int size);

    // MatchBoard 수락 기능
    ResponseEntity upStatus(Long boardId, Long matchboardId, Long userId);

    // MatchBoard 거절 기능
    ResponseEntity downStatus(Long boardId, Long matchboardId, Long userId);
}
