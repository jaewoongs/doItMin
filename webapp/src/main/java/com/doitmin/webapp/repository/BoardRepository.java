package com.doitmin.webapp.repository;

import com.doitmin.webapp.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
