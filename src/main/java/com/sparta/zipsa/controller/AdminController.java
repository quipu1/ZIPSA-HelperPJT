package com.sparta.zipsa.controller;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    //전체 사용자 조회
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUserAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
            ) {
        return adminService.getUserAll(page, size, sortBy, isAsc);
    }

}
