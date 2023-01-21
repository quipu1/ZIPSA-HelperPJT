package com.sparta.zipsa.controller;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //프로필 조회
    @GetMapping("/{userId}")
    public ProfileResponseDto getProfile(@PathVariable Long userId) {
        return userService.getProfile(userId);
    }

   //프로필 수정
    @PutMapping("/{userId}")
    public ResponseEntity updateProfile(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile multipartFile,
            @RequestPart("profileRequestDto") @Valid ProfileRequestDto profileRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails

    ) {
        return userService.updateProfile(userId, multipartFile, profileRequestDto, userDetails.getUser());
    }


    //심부름 요청글 올린 Customer 전체 조회
    @GetMapping("/customer")
    public List<UserResponseDto> getApplyCustomers(
            @RequestParam(value = "search", defaultValue = "") String search, //쿼리스트링 입력 안할 시 디폴트값
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc

    ) {
        return userService.getApplyCustomers(search,page-1, size, isAsc);
    }

    //헬퍼 전체 조회
    @GetMapping("/helper")
    public List<UserResponseDto> getHelpers(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc
    ) {
        return userService.getHelpers(search,page-1, size, isAsc);
    }


}