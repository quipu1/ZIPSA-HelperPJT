package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.zipsa.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    Page<Board> findAll(Pageable pageable);
    Page<Board> findAllByUsername(Pageable pageable, String username);
    List<Board> findAllByUsername(String username);
}
