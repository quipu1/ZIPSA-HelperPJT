package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequestDto;
import com.sparta.zipsa.dto.BoardResponseDto;
import com.sparta.zipsa.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface BoardService {
    BoardResponseDto createBoard( BoardRequestDto boardRequest,  User user);

    BoardResponseDto revisionBoard(Long boardId, BoardRequestDto boardRequest, User user);

    String deleteBoard(Long boardId, User user);

}
