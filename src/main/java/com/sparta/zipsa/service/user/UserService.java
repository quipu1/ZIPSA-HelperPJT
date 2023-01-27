package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.PasswordRequestDto;
import com.sparta.zipsa.dto.ProfileRequestDto;
import com.sparta.zipsa.dto.ProfileResponseDto;
import com.sparta.zipsa.dto.UserResponseDto;
import com.sparta.zipsa.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    ProfileResponseDto getProfile(Long userId);

    User getUser(Long userId);

    List<UserResponseDto> getApplyCustomers(String search, int page, int size, boolean isAsc);

    List<UserResponseDto> getHelpers(String search, int page, int size, boolean isAsc);

    ResponseEntity updateProfile(Long userId, MultipartFile multipartFile, ProfileRequestDto profileRequestDto, User user);

    ResponseEntity modifyPassword(Long userId, User user, PasswordRequestDto passwordRequestDto);
}


