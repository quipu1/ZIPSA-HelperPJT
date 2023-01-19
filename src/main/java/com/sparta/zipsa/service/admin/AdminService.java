package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    Page<User> getUserAllByRole(int page, int size, boolean isAsc, String role);
    List<HelperBoard> getHelperBoard();
    void acceptHelperAuthority(Long userId);
    void removeHelperAuthority(Long userId);
}