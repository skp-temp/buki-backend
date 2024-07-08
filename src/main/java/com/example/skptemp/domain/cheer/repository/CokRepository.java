package com.example.skptemp.domain.cheer.repository;

import com.example.skptemp.domain.cheer.entity.Cok;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CokRepository extends JpaRepository<Cok, Long> {
    Optional<Cok> findByUserIdAndCharmIdAndDate(Long userId, Long charmId, LocalDate date);
}
