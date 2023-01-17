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

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUserAll(int page, int size, String sortBy, boolean isAsc) {
        //페이징 처리
        //삼항연산자로 true ASC / false DESC 정렬 설정
        //sortBy로 정렬 기준이 되는 property 설정
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users = userRepository.findAll(pageable);

        return users;
    }
}
