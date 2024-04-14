package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.Charm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharmRepository extends JpaRepository<Charm, Long>, QuerydslCharmRepository {
    Optional<Charm> findByIdAndIsValidIsTrue(Long id);
}