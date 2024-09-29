package com.example.skptemp.domain.charm.repository;

import com.example.skptemp.domain.charm.entity.CharmItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharmItemRepository extends JpaRepository<CharmItem, Long> ,CharmItemCustomRepository{
    long deleteByCharmId(Long charmId);


    List<CharmItem> findByCharmIdIn(List<Long> charmId);
}
