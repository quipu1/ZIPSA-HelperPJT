package com.sparta.zipsa.controller;


import com.sparta.zipsa.dto.DeleteRequestDto;
import com.sparta.zipsa.dto.LoginRequestDto;
import com.sparta.zipsa.dto.LoginResponseDto;
import com.sparta.zipsa.dto.SignupRequestDto;
import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.auth.AuthService;
import com.sparta.zipsa.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestPart("file") MultipartFile multipartFile, //사진
                                 //dto객체와 file객체 동시에 받으려면 @RequestPart 필요
                                 @RequestPart("signupRequestDto") @Valid SignupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto, multipartFile);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        LoginResponseDto loginDto = authService.login(loginRequestDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginDto.getUsername(), loginDto.getRole()));
        return new ResponseEntity("로그인 되었습니다.", HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        // 정보를 컨트롤러로 보냈을 때 HttpServletRequest 객체 안에 모든 데이터가 들어가게 된다.

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 등록된 세션을 삭제한다.
        }
        return new ResponseEntity("로그아웃 되었습니다.", HttpStatus.OK);
    }

    //회원탈퇴
    @DeleteMapping("/delete")
    //@AuthenticationPrincipal -> 세션 정보 UserDetails에 접근할 수 있는 어노테이션
    //현재 로그인한 사용자 객체를 가져오기 위해 필요
    public ResponseEntity delete(@RequestBody DeleteRequestDto deleteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return authService.delete(deleteRequestDto, userDetails.getUser());
    }
}
