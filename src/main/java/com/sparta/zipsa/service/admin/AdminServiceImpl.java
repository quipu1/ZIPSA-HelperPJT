package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.dto.TokenRequestDto;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final HelperBoardRepository helperBoardRepository;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUserAllByRole(int page, int size, boolean isAsc, String role) {
        //페이징 처리
        //삼항연산자로 true ASC / false DESC 정렬 설정
        //sortBy로 정렬 기준이 되는 property 설정 - id
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        if (role.equals("customer")) {
            Page<User> users = userRepository.findByUserRoleEnum(pageable, UserRoleEnum.CUSTOMER);
        } else if (role.equals("helper")) {
            Page<User> users = userRepository.findByUserRoleEnum(pageable, UserRoleEnum.HELPER);
        } else if (role.equals("admin")) {
            Page<User> users = userRepository.findByUserRoleEnum(pageable, UserRoleEnum.ADMIN);
        } else throw new IllegalArgumentException();

        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HelperBoard> getHelperBoard() {
        List<HelperBoard> helperBoardList = helperBoardRepository.findAll();
        return helperBoardList;
    }

    @Override
    @Transactional
    public void acceptHelperAuthority(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저는 존재하지 않습니다."));

        if (user.getRole.equals(UserRoleEnum.CUSTOMER)) {
            user.changeRole(UserRoleEnum.HELPER);
        } else {
            throw new IllegalArgumentException("이미 Helper 권한을 가진 유저입니다.");
        }
    }

    @Override
    @Transactional
    public void removeHelperAuthority(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저는 존재하지 않습니다."));

        if (user.getRole.equals(UserRoleEnum.HELPER)) {
            user.changeRole(UserRoleEnum.CUSTOMER);
        } else {
            throw new IllegalArgumentException("이미 CUSTOMER 권한을 가진 유저입니다.");
        }
    }

    @Transactional
    public void reIssue(TokenRequestDto tokenRequestDto, HttpServletResponse response) {
        if(!jwtUtil.validateTokenExceptExpiration(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        User user = findUserByToken(tokenRequestDto);

        if(!user.getRefreshToken().equals(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        String refreshToken = jwtUtil.createRefreshToken();

        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);

        addTokenToHeader(response, user);
    }
}