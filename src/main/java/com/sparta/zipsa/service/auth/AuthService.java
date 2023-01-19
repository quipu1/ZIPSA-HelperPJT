package com.sparta.zipsa.service.auth;

import com.sparta.zipsa.dto.DeleteRequestDto;
import com.sparta.zipsa.dto.LoginRequestDto;
import com.sparta.zipsa.dto.LoginResponseDto;
import com.sparta.zipsa.dto.SignupRequestDto;
import com.sparta.zipsa.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

    ResponseEntity signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    ResponseEntity delete(DeleteRequestDto deleteRequestDto, User user);
}
