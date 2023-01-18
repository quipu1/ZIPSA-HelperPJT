package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.MatchBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchBoardRepository extends JpaRepository<MatchBoard, Long> {
    List<MatchBoard> findAllByMatchBoard();

}
