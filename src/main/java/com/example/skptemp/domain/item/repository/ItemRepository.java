package com.example.skptemp.domain.item.repository;

import com.example.skptemp.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
