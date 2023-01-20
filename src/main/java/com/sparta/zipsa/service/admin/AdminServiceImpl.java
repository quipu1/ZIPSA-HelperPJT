package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.dto.HelperBoardResponseDto;
import com.sparta.zipsa.dto.UserResponseDto;
import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.AdminException;
import com.sparta.zipsa.exception.HelperException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.HelperBoardRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final HelperBoardRepository helperBoardRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUserAllByRole(int page, int size, boolean isAsc, String role) {
        //페이징 처리
        //삼항연산자로 true ASC / false DESC 정렬 설정
        //sortBy로 정렬 기준이 되는 property 설정 - id
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users;

        if (role.equals("customer")) {
            users = userRepository.findByRole(UserRoleEnum.CUSTOMER, pageable);
        } else if (role.equals("helper")) {
            users = userRepository.findByRole(UserRoleEnum.HELPER, pageable);
        } else if (role.equals("admin")) {
            users = userRepository.findByRole(UserRoleEnum.ADMIN, pageable);
        } else {
            throw new AdminException.InvalidAuthorityException();
        }

        Page<UserResponseDto> userDtos = users.map(m -> new UserResponseDto(m));

        return userDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelperBoardResponseDto> getHelperBoard() {
        List<HelperBoard> helperBoardList = helperBoardRepository.findAll();
        List<HelperBoardResponseDto> helperBoardResponseDtoList = helperBoardList.stream()
                .map(m -> new HelperBoardResponseDto(m))
                .collect(Collectors.toList());
        return helperBoardResponseDtoList;
    }

    @Override
    @Transactional
    public ResponseEntity acceptHelperAuthority(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);

        if (user.getRole().equals(UserRoleEnum.CUSTOMER)) {
            user.changeRole(UserRoleEnum.HELPER);
            helperBoardRepository.deleteByUsername(user.getUsername());
        } else {
            throw new HelperException.AlreadyHelperException();
        }

        return new ResponseEntity("해당 유저가 HELPER 권한으로 등록되었습니다.", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity removeHelperAuthority(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);

        if (user.getRole().equals(UserRoleEnum.HELPER)) {
            user.changeRole(UserRoleEnum.CUSTOMER);
        } else {
            throw new HelperException.AlreadyCustomerException();
        }

        return new ResponseEntity("해당 유저가 CUSTOMER 권한으로 등록되었습니다.", HttpStatus.OK);
    }
}