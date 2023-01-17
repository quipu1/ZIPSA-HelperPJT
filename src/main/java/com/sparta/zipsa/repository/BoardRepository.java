package com.sparta.zipsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.zipsa.entity.Board;
public interface BoardRepository extends JpaRepository<Board,Long> {

}
