package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public List<UserResponseDto> getApplyCustomers(int page, int size, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "address");
        Pageable pageable = PageRequest.of(page, size, sort);

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        Page<User> users = userRepository.findDistinctByRoleAndBoardsIsNotNull(UserRoleEnum.CUSTOMER, pageable);

        for (User user : users) {
            userResponseDtoList.add(new UserResponseDto(user));
        }

        return userResponseDtoList;
    }

    //Helper 조회
    @Override
    public List<UserResponseDto> getHelpers(int page, int size, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "address");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users = userRepository.findByRole(UserRoleEnum.HELPER, pageable);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();


        for (User user : users) {
            userResponseDtoList.add(new UserResponseDto(user));

        }
        return userResponseDtoList;
    }
}


