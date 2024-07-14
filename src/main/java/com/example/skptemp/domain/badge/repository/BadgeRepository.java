package com.example.skptemp.domain.badge.repository;

import com.example.skptemp.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findByIdAndIsValidIsTrue(Long id);
    List<Badge> findByIsValidIsTrue();
}
