package com.doitmin.webapp.api.repository;

import com.doitmin.webapp.api.entities.Board;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Id> {
    List<Board> findAllByOrderByIdDesc();
}
