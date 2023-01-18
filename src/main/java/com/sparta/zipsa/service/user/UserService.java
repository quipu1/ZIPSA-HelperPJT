package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {

    public ResponseEntity signup(SignupRequestDto signupRequestDto);

    public LoginResponseDto login(LoginRequestDto loginRequestDto);


    public ProfileResponseDto getProfile(Long user_id);

    public ResponseEntity delete(DeleteRequestDto deleteRequestDto, User user);


    public ResponseEntity updateProfile(Long user_id, ProfileRequestDto requestDto, UserDetailsImpl userDetails);


    public Page<Board> getPageBoardByUser(Long user_id, int page, int size, boolean isAsc);
}


