package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.AdminException;
import com.sparta.zipsa.exception.HelperException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.UserRepository;
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

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUserAllByRole(int page, int size, boolean isAsc, String role) {
        //페이징 처리
        //삼항연산자로 true ASC / false DESC 정렬 설정
        //sortBy로 정렬 기준이 되는 property 설정 - id
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users;

        if (role.equals("customer")) {
            users = userRepository.findByUserRoleEnum(UserRoleEnum.CUSTOMER, pageable);
        } else if (role.equals("helper")) {
            users = userRepository.findByUserRoleEnum(UserRoleEnum.HELPER, pageable);
        } else if (role.equals("admin")) {
            users = userRepository.findByUserRoleEnum(UserRoleEnum.ADMIN, pageable);
        } else {
            throw new AdminException.InvalidAuthorityException();
        }

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
        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);

        if (user.getRole().equals(UserRoleEnum.CUSTOMER)) {
            user.changeRole(UserRoleEnum.HELPER);
        } else {
            throw new HelperException.AlreadyHelperException();
        }
    }

    @Override
    @Transactional
    public void removeHelperAuthority(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);

        if (user.getRole().equals(UserRoleEnum.HELPER)) {
            user.changeRole(UserRoleEnum.CUSTOMER);
        } else {
            throw new HelperException.AlreadyCustomerException();
        }
    }
}