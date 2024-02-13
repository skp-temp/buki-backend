package com.example.skptemp.domain.item.repository;

import com.example.skptemp.domain.item.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
}
