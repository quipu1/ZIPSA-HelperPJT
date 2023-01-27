package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchBoardRepository extends JpaRepository<MatchBoard, Long> {

    Boolean existsByUsernameAndBoard(String username, Board board);

    @NotNull Page<MatchBoard> findAll(@NotNull Pageable pageable);

    List<MatchBoard> findByBoardAndStatus(Board board, String status);
}
