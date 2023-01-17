package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.BoardRequest;
import com.sparta.zipsa.dto.BoardResponse;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface BoardService {
    BoardResponse createBoard(@RequestBody BoardRequest boardRequest, @AuthenticationPrincipal User user);

}
