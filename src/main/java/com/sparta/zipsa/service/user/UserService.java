package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseEntity signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile);

    LoginResponseDto login(LoginRequestDto loginRequestDto);


    ProfileResponseDto getProfile(Long userId);

    ResponseEntity delete(DeleteRequestDto deleteRequestDto, User user);


    ResponseEntity updateProfile(Long userId, ProfileRequestDto profileRequestDto, User user);


    Page<Board> getPageBoardByUser(Long userId, int page, int size, boolean isAsc, User user);
}


