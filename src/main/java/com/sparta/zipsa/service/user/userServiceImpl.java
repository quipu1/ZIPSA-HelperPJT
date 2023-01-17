package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.repository.UserRepository;
import com.sparta.zipsa.security.UserDetailsImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements userService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "으아아아아아아아악ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ";
    @Override
    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword(); // 시큐리티 적용시 .encode하기
        String address = signupRequestDto.getAddress();
        String user_img = signupRequestDto.getUser_img();



        Optional<User> foundUser = userRepository.findByUsername(username); //.isPresent같은 메소드 사용가능해서 optional
        if(foundUser.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }
        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new SecurityException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }


        User user = new User(username, password, address, user_img, role);
        userRepository.save(user);
        return new MessageResponseDto("회원 가입 완료",200);

        }

    @Override
    public MessageResponseDto login(LoginRequestDto loginRequestDto, HttpServeletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다")
        );

//        if(!일단 놔뒀다가 시큐리티 되면 encode된 비밀번호 확인 메소드 넣기)

        return new MessageResponseDto(jwtUtil.createToken(user.getUsername(),user.getRole()));
    }

    @Override
    public ProfileResponseDto getProfile(Long user_id) {
        return null;
    }

    @Override
    public MessageResponseDto updateProfile(Long user_id, ProfileRequestDto requestDto, UserDetailsImpl userDetails) {
        return null;
    }

    @Override
    public Page<Board> getPageBoardByUser(Long user_id, int page, int size, boolean isAsc) {
        return null;
    }


}
