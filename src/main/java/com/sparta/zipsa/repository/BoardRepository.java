package com.sparta.zipsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.zipsa.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findBoardsByUsernameOrderByCreatedAt(Board user);
}
