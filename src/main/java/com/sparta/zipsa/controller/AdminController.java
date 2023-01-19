package com.sparta.zipsa.controller;

import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    //요청된 Role에 따라 해당 Role의 사용자 전체 조회
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUserAllByRoll(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("isAsc") boolean isAsc,
            @RequestParam("role") String role
    ) {
        return adminService.getUserAllByRole(page, size, isAsc, role);
    }

    //helper 신청글 조회
    @GetMapping("/helperboard")
    @ResponseStatus(HttpStatus.OK)
    public List<HelperBoard> getHelperBoard() {
        return adminService.getHelperBoard();
    }

    //helper 권한 등록 요청 승인
    @PutMapping("/authority/accept/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptHelperAuthority(@PathVariable Long userId) {
        adminService.acceptHelperAuthority(userId);
    }

    @PutMapping("/authorty/remove/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeHelperAuthority(@PathVariable Long userId) {
        adminService.removeHelperAuthority(userId);
    }

}