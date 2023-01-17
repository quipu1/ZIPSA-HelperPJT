package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.entity.User;
import org.springframework.data.domain.Page;

public interface AdminService {
    Page<User> getUserAll(int page, int size, String sortBy, boolean isAsc);
}
