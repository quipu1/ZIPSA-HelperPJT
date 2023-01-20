package com.sparta.zipsa.service.admin;

import com.sparta.zipsa.dto.HelperBoardResponseDto;
import com.sparta.zipsa.dto.UserResponseDto;
import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    Page<UserResponseDto> getUserAllByRole(int page, int size, boolean isAsc, String role);
    List<HelperBoardResponseDto> getHelperBoard();
    ResponseEntity acceptHelperAuthority(Long userId);
    ResponseEntity removeHelperAuthority(Long userId);
}