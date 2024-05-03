package com.example.skptemp.domain.badge.repository;

import com.example.skptemp.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
