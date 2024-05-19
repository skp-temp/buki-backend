package com.example.skptemp.domain.badge.repository;

import com.example.skptemp.domain.badge.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUserIdAndIsValidIsTrue(Long userId);
}
