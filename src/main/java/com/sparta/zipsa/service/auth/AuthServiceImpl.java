package com.sparta.zipsa.service.auth;

import com.sparta.zipsa.dto.DeleteRequestDto;
import com.sparta.zipsa.dto.LoginRequestDto;
import com.sparta.zipsa.dto.LoginResponseDto;
import com.sparta.zipsa.dto.SignupRequestDto;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.AdminException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.UserRepository;
import com.sparta.zipsa.service.file.FileService;
import com.sparta.zipsa.service.helperBoard.HelperBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final HelperBoardService helperBoardService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final UserRepository userRepository;

    //회원가입 기능. 이미지 파일 + json입력방식
    @Override
    @Transactional
    public ResponseEntity signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String address = signupRequestDto.getAddress();
        String uuid = UUID.randomUUID().toString(); //파일 이름 중복될 수 있으니 고유 식별자 넣기 위함
        String uniqueInfo = uuid.substring(0, 7); // 너무 기니까 6번인 덱스까지만
        String fileName = uniqueInfo + "-" + multipartFile.getOriginalFilename(); //uuid + 원본 파일 이름

        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent()) {
            throw new UserException.UsernameDuplicateException();
        }
        UserRoleEnum role = UserRoleEnum.CUSTOMER; // 기본은 CUSTOMER, 토큰 일치하면 ADMIN으로 바꿔줌
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new AdminException.AdminPasswordNotMatchException();
            }
            role = UserRoleEnum.ADMIN;
        }
        fileService.constructor(); //저장경로 없으면 만들어주려나 포스트맨 돌려보고 싶은데..
        fileService.upload(multipartFile, fileName); //업로드할 사진, uuid 적용한 파일이름
        String userImage = fileName; //User에 넣어줄 userImage에는 파일 이름을 넣어줬음
        User user = new User(username, password, address, userImage, role);
        userRepository.save(user);
        return new ResponseEntity("회원 가입 완료", HttpStatus.OK);

    }

    //로그인 기능
    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(UserException.UserNotFoundException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException.PasswordNotMatchException();
        }
        return new LoginResponseDto(user.getUsername(), user.getPassword(), user.getRole());
    }

    //회원 탈퇴 기능
    @Override
    @Transactional
    public ResponseEntity delete(DeleteRequestDto deleteRequestDto, User user) {
        //현재 로그인한 유저와 유저네임, 비밀번호가 일치하거나, ADMIN 권한이 있을 경우 회원 탈퇴 기능 가능
        if (user.getRole().equals(UserRoleEnum.ADMIN)
                || user.getUsername().equals(deleteRequestDto.getUsername())
                && passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {
            userRepository.deleteByUsername(deleteRequestDto.getUsername());
            helperBoardService.deleteByUsername(deleteRequestDto.getUsername()); // 유저 탈퇴시, 해당 유저가 올린 helperBoard도 같이 삭제됨
            fileService.deleteFile(user.getUserImage()); // 이렇게 하면 파일도 삭제되려나.. 포스트맨으로 돌려보고 싶은데..
            return new ResponseEntity("회원 탈퇴 처리 되었습니다", HttpStatus.OK);
        }
        throw new UserException.UserNotFoundException();
    }
}
