package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.entity.User;
import org.springframework.data.domain.Page;

public interface AdminService {
    Page<User> getUserAllByRole(int page, int size, boolean isAsc, String role);
    List<HelperBoard> getHelperBoard();
}
