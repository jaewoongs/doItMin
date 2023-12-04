package com.doitmin.webapp.api.repository;

import com.doitmin.webapp.api.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
