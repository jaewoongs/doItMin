package com.doitmin.webapp.api.repository;

import com.doitmin.webapp.api.entities.RefreshToken;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Id> {
    RefreshToken findByRefreshTokenAndExpiredAtLessThanOrderByIdDesc(String refreshToken, Date now);
}
