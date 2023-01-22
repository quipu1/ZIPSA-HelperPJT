package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.BoardException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.UserRepository;
import com.sparta.zipsa.service.file.FileService;
import lombok.Getter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final FileService fileService;

    private final PasswordEncoder passwordEncoder;

    //프로필 조회 기능
    @Override
    @Transactional
    public ProfileResponseDto getProfile(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);
        return new ProfileResponseDto(user);
    }

    //프로필 수정 기능
    @Override
    @Transactional
    public ResponseEntity updateProfile(Long userId, MultipartFile multipartFile, ProfileRequestDto profileRequestDto, User user) {
        String uuid = UUID.randomUUID().toString();
        String uniqueInfo = uuid.substring(0, 7);
        String fileName = uniqueInfo + "-" + multipartFile.getOriginalFilename();

        if (userId != user.getId() && !user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new UserException.AuthorityException();
        }
        if (!multipartFile.isEmpty()) {
            fileService.constructor();
            fileService.upload(multipartFile, fileName);
            user.update(profileRequestDto, fileName);
        } else user.updateNotImage(profileRequestDto);
        userRepository.save(user);
        return new ResponseEntity("프로필 수정이 완료됐습니다", HttpStatus.OK);
    }
    //비밀번호 변경
    @Override
    @Transactional
    public ResponseEntity modifyPassword(Long userId,User user,PasswordRequestDto passwordRequestDto) {
            if(userId == user.getId()){
            user.modifyPassword(passwordEncoder.encode(passwordRequestDto.getPassword()));
            userRepository.save(user);
            return new ResponseEntity("비밀번호가 수정되었습니다.", HttpStatus.OK);
        } else throw new UserException.AuthorityException();
    }

    //심부름 요청글 올린 Customer 전체 조회
    @Override
    public List<UserResponseDto> getApplyCustomers(String search, int page, int size, boolean isAsc) {
        page = Math.max(page, 0);
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "address");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        Page<User> users = userRepository.findDistinctByRoleAndBoardsIsNotNullAndAddressContaining(UserRoleEnum.CUSTOMER, search, pageable);
        for (User user : users) {
            userResponseDtoList.add(new UserResponseDto(user));
        }

        return userResponseDtoList;
    }

    //Helper 전체 조회
    @Override
    public List<UserResponseDto> getHelpers(String search, int page, int size, boolean isAsc) {
        page = Math.max(page, 0);
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "address");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users = userRepository.findByRoleAndAddressContaining(UserRoleEnum.HELPER, search, pageable);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();


        for (User user : users) {
            userResponseDtoList.add(new UserResponseDto(user));

        }
        return userResponseDtoList;
    }
}


