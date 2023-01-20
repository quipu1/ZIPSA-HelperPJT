package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HelperBoardRepository extends JpaRepository<HelperBoard, Long> {

    Optional<HelperBoard> findByUsername(String username);

    void deleteByUsername(String username);
}
