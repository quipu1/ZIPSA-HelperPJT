package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 수락 및 거절 기능 Repository
public interface HelpStatusRepository extends JpaRepository<Status, Long> {
    List<Status> findByUserAndMatchBoard(Board board, MatchBoard matchBoard);
}
