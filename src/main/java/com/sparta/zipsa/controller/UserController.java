package com.sparta.zipsa.controller;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestBody ProfileRequestDto profileRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.updateProfile(userId, profileRequestDto, userDetails.getUser());
    }


    //심부름 요청글 올린 Customer 전체 조회
    @GetMapping("/customer/{status}")
    public Page<User> getApplyCustomers(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc

    ) {
        return userService.getApplyCustomers(page, size, isAsc, userDetails.getUser());
    }

    //Helper 조회
    @GetMapping("/helper")
    public Page<User> getHelpers(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc
    ) {
        return userService.getHelpers(page, size, isAsc);
    }


}