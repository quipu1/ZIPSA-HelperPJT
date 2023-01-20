package com.sparta.zipsa.service.helperBoard;

import com.sparta.zipsa.dto.DeleteRequestDto;
import com.sparta.zipsa.dto.HelperBoardRequestDto;
import com.sparta.zipsa.dto.HelperBoardResponseDto;
import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface HelperBoardService {
    ResponseEntity createHelperBoard(HelperBoardRequestDto helperBoardRequestDto, User user);

    ResponseEntity deleteHelperBoard(Long helperBoardId, User user);

    ResponseEntity updateHelperBoard(Long helperBoardId, HelperBoardRequestDto helperBoardRequestDto, User user);

    void deleteByUsername(String username);

    boolean isAuthorized(User user, HelperBoard helperBoard);

    HelperBoardResponseDto getHelperBoard(Long boardId, User user);
}
