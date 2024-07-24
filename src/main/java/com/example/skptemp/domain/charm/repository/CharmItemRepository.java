package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.CharmItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharmItemRepository extends JpaRepository<CharmItem, Long> {
    long deleteByCharmId(Long charmId);
}
