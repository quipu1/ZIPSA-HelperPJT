package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByUserRoleEnum(UserRoleEnum role, Pageable pagable);

    Optional<User> findByUsername(String username);

}
