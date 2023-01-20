package com.sparta.zipsa.controller;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody ProfileRequestDto profileRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.updateProfile(userId, profileRequestDto, userDetails.getUser());
    }


    //심부름 요청글 올린 Customer 전체 조회
    @GetMapping("/customer")
    public List<UserResponseDto> getApplyCustomers(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc

    ) {
        return userService.getApplyCustomers(page-1, size, isAsc);
    }

    //Helper 조회
    @GetMapping("/helper")
    public Page<User> getHelpers(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc
    ) {
        return userService.getHelpers(page-1, size, isAsc);
    }
//    @GetMapping("/helper")
//    public List<UserResponseDto> getHelpers(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam("isAsc") boolean isAsc
//    ) {
//        return userService.getHelpers(page-1, size, isAsc);
//    }


}