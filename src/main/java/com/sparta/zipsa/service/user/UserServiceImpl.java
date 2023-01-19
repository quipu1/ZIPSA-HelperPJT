package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.AdminException;
import com.sparta.zipsa.exception.HelperException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.UserRepository;
import com.sparta.zipsa.service.file.FileService;
import com.sparta.zipsa.service.helperBoard.HelperBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //프로필 조회 기능
    @Override
    @Transactional
    public ProfileResponseDto getProfile(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);
        return new ProfileResponseDto(user);
    }

    //프로필 업데이트 기능
    @Override
    @Transactional
    public ResponseEntity updateProfile(Long userId, ProfileRequestDto profileRequestDto, User user) {
        if (user.getId() == userId || user.getRole().equals(UserRoleEnum.ADMIN)) {
            user.update(profileRequestDto);
            return new ResponseEntity("프로필 수정이 완료됐습니다.", HttpStatus.OK);
        }
        throw new UserException.AuthorityException();
    }

    //심부름 요청글 올린 Customer 전체 조회
    @Override
    public Page<User> getApplyCustomers(int page, int size, boolean isAsc, User user) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "Id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users;
        users = userRepository.findByBoardsIsNotNull(user.getBoards(), pageable);
        return users;

    }

    //Helper 조회
    @Override
    public Page<User> getHelpers(int page, int size, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "Id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users;
        users = userRepository.findByRole(UserRoleEnum.HELPER, pageable);
        return users;

    }
}


