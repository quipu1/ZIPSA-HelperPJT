package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.MatchBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchBoardRepository extends JpaRepository<MatchBoard, Long> {
    Page<MatchBoard> findAll(Pageable pageable);
    List<MatchBoard> findByStatus(String status);
}
