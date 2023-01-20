package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    ProfileResponseDto getProfile(Long userId);

    ResponseEntity updateProfile(Long userId, ProfileRequestDto profileRequestDto, User user);

    List<UserResponseDto> getApplyCustomers(int page, int size, boolean isAsc);

    Page<User> getHelpers(int page, int size, boolean isAsc);
//    List<UserResponseDto> getHelpers(int page, int size, boolean isAsc);
}


