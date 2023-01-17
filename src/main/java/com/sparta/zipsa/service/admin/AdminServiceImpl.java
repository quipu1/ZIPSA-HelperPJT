package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.entity.User;
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
}
