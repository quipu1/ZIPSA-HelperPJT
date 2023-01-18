package com.sparta.zipsa.controller;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.service.user.UserService;
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
    private final JwtUtil jwtUtil;



    @PostMapping("/signup")
    public ResponseEntity signup(@RequestPart("file") MultipartFile multipartFile, //사진
                                 //dto객체와 file객체 동시에 받으려면 @RequestPart 필요
                                 @RequestPart("signupRequestDto") @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto, multipartFile);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        LoginResponseDto loginDto = userService.login(loginRequestDto);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginDto.getUsername(), loginDto.getRole()));
        return new ResponseEntity("로그인 되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public ProfileResponseDto getProfile(@PathVariable Long user_id) {

        return userService.getProfile(user_id);

    }

    @PutMapping("/{user_id}")
    public ResponseEntity updateProfile(
            @PathVariable Long user_id,
            @RequestBody ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {

        return userService.updateProfile(user_id, requestDto, userDetails);

    }

    @GetMapping("/{user_id}/boards")
    public Page<Board> getPageBoardByUser(
            @PathVariable Long user_id,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc
    ) {
        return userService.getPageBoardByUser(user_id, page, size, isAsc);
    }

    @DeleteMapping("/delete")
    //@AuthenticationPrincipal -> 세션 정보 UserDetails에 접근할 수 있는 어노테이션
    //현재 로그인한 사용자 객체를 가져오기 위해 필요
    public ResponseEntity delete(@RequestBody DeleteRequestDto deleteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.delete(deleteRequestDto, userDetails.getUser());
    }


}