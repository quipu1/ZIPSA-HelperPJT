package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.AdminException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.repository.UserRepository;
import com.sparta.zipsa.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileService fileServiceImpl;

    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Override
    @Transactional
    public ResponseEntity signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String address = signupRequestDto.getAddress();
        String uuid = UUID.randomUUID().toString();
        String uniqueInfo = uuid.substring(0, 7);
        String fileName = uniqueInfo + "-" + multipartFile.getOriginalFilename();

        Optional<User> foundUser = userRepository.findByUsername(username); //.isPresent같은 메소드 사용가능해서 optional
        if (foundUser.isPresent()) {
            throw new UserException.UsernameDuplicateException();
        }
        UserRoleEnum role = UserRoleEnum.CUSTOMER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new AdminException.AdminPasswordNotMatchException();
            }
            role = UserRoleEnum.ADMIN;
        }
        fileServiceImpl.upload(multipartFile,fileName); //업로드할 사진, uuid적용한 이름
        String userImage = fileName;
        User user = new User(username, password, address, userImage, role);
        userRepository.save(user);
        return new ResponseEntity("회원 가입 완료", HttpStatus.OK);

    }

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

    @Override
    @Transactional
    public ResponseEntity delete(DeleteRequestDto deleteRequestDto, User user) {

        if (user.getRole().equals(UserRoleEnum.ADMIN)
                || user.getUsername().equals(deleteRequestDto.getUsername())
                && passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {
            userRepository.deleteByUsername(deleteRequestDto.getUsername());
            return new ResponseEntity("회원 탈퇴 처리 되었습니다", HttpStatus.OK);
        }
        throw new UserException.UserNotFoundException();
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

