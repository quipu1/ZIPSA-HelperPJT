package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface MatchBoardService {

    // MatchBoard 생성
    MatchBoardResponseDto createMatchBoard(Long boardId, MatchBoardRequestDto requestDto, UserDetails userDetails);

    // MatchBoard 조회
    MatchBoardResponseDto getMatchBoard(Long boardId, Long matchBoardId);

    // MatchBoard 수정
    MatchBoardResponseDto updateMatchBoard(Long boardId, Long matchBoardId, MatchBoardRequestDto requestDto, UserDetails userDetails);

    // MatchBoard 삭제
    ResponseEntity deleteMatchBoard(Long boardId, Long matchBoardId,UserDetails userDetails);


    // 심부름 수락 기능
   boolean addStatus(Long boardId, Long matchboardId);

   // 심부름 거절 기능
   boolean deleteStatus(Long boardId, Long matchboardId);
}
