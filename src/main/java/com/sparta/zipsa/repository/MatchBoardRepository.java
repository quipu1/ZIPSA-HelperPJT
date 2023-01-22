package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchBoardRepository extends JpaRepository<MatchBoard, Long> {

    Optional<MatchBoard> findByUsernameAndBoard(String username, Board board);
    @NotNull Page<MatchBoard> findAll(@NotNull Pageable pageable);
    List<MatchBoard> findByBoardAndStatus(Board board, String status);
}
