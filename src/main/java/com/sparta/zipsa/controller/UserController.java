package com.sparta.zipsa.controller;


import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userServiceImpl;
    private final JwtUtil jwtUtil;


    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userServiceImpl.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        LoginResponseDto loginDto = userServiceImpl.login(loginRequestDto);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginDto.getUsername(),loginDto.getRole()));
        return new ResponseEntity("로그인 되었습니다.",HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public ProfileResponseDto getProfile(@PathVariable Long user_id) {

        return userServiceImpl.getProfile(user_id);

    }

    @PutMapping("/{user_id}")
    public ResponseEntity updateProfile(
            @PathVariable Long user_id,
            @RequestBody ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return userServiceImpl.updateProfile(user_id, requestDto, userDetails);

    }

    @GetMapping("/{user_id}/boards")
    public Page<Board> getPageBoardByUser(
            @PathVariable Long user_id,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc
    ) {
        return userServiceImpl.getPageBoardByUser(user_id, page, size, isAsc);
    }

    @DeleteMapping("/delete")
    //@AuthenticationPrincipal -> 세션 정보 UserDetails에 접근할 수 있는 어노테이션
    //현재 로그인한 사용자 객체를 가져오기 위해 필요
    public ResponseEntity delete(@RequestBody DeleteRequestDto deleteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userServiceImpl.delete(deleteRequestDto, userDetails.getUser());
    }


}