package com.sparta.zipsa.repository;

import com.sparta.zipsa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.zipsa.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findBoardsByUsername(String userName);

}
