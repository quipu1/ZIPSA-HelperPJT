package com.sparta.zipsa.controller;


import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.admin.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        MessageResponseDto msg = userService.signup(signupRequestDto);
        return msg;

    }

    @PostMapping("/login")
    public MessageResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServeletResponse response) {
        MessageResponseDto msg = userService.login(loginRequestDto);
        String token = msg.getMessage();
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        return new MessageResponseDto("로그인 되었습니다", 200);
    }

    @GetMapping("/{user_id}")
    public ProfileResponseDto getProfile(@PathVariable Long user_id) {

        return userService.getProfile(user_id);

    }

    @PutMapping("/{user_id}")
    public MessageResponseDto updateProfile(
            @PathVariable Long user_id,
            @RequestBody ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return userService.updateProfile(id, requestDto, userDetails);

    }

    @GetMapping("/{user_id}/boards?sortBy=String&isAsc=boolean&size=int&page=int")
    public Page<Board> getPageBoardByUser(
            @PathVariable Long user_id,
//            @RequestParam("board??????") // 물어봐야징
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc
            ) {
        return userService.getPageBoardByUser(page, size, isAsc);
    }

}