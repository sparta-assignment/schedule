package org.sparta.schedule.repository;

import org.sparta.schedule.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUser_Id(Long userId);
}
