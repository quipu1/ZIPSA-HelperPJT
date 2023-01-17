package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface userService {

    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto);

    public MessageResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServeletResponse response);


    public ProfileResponseDto getProfile(@PathVariable Long user_id);


    public MessageResponseDto updateProfile(
            @PathVariable Long user_id,
            @RequestBody ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    );


    public Page<Board> getPageBoardByUser(
            @PathVariable Long user_id,
//            @RequestParam("board??????") // 물어봐야징
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc
    );
}
