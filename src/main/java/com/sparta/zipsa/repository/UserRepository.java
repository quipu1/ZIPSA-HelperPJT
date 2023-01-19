package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByRole(UserRoleEnum role, Pageable pageable);

    Optional<User> findByUsername(String username);

    User deleteByUsername(String username);

    Page<User> findByBoardsIsNotNull(List<Board> boards, Pageable pageable);
}