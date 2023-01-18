package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "으아아아아아아아악ㅏㅏㅏㅏㅏㅏ";
    @Override
    @Transactional
    public ResponseEntity signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword(); /
        String address = signupRequestDto.getAddress();
        String user_img = signupRequestDto.getUser_img();



        Optional<User> foundUser = userRepository.findByUsername(username); //.isPresent같은 메소드 사용가능해서 optional
        if(foundUser.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }
        UserRoleEnum role = UserRoleEnum.CUSTOMER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new SecurityException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }


        User user = new User(username, password, address, user_img, role);
        userRepository.save(user);
//        return new MessageResponseDto("회원 가입 완료",200);
        return new ResponseEntity<>("회원 가입 완료", HttpStatus.OK);

    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다")
        );
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new LoginResponseDto(jwtUtil.createToken(user.getUsername(),user.getRole()));
    }

    @Override
    @Transactional
    public ResponseEntity delete(DeleteRequestDto deleteRequestDto, User user){

        if (user.getRole().equals(UserRoleEnum.ADMIN)
                || user.getUsername().equals(deleteRequestDto.getUsername())
                &&passwordEncoder.matches(deleteRequestDto.getPassword(),user.getPassword())) {
            userRepository.deleteByUsername(deleteRequestDto.getUsername());
            return new ResponseEntity("회원 탈퇴 처리 되었습니다", HttpStatus.OK);
//            return new MessageResponseDto("삭제 성공", 200);
        }
        throw new SecurityException("가입한 회원만이 탈퇴할 수 있습니다");
    }

    @Override
    public ProfileResponseDto getProfile(Long user_id) {
        return null;
    }

    @Override
    public ResponseEntity updateProfile(Long user_id, ProfileRequestDto requestDto, UserDetailsImpl userDetails) {
        return null;
    }

    @Override
    public Page<Board> getPageBoardByUser(Long user_id, int page, int size, boolean isAsc) {
        return null;
    }

}

