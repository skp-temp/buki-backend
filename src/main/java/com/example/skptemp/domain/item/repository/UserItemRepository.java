package com.example.skptemp.domain.item.repository;

import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.global.constant.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long>,UserItemCustomRepository {
    List<UserItem> findByUserId(Long userId);
    Optional<UserItem> findByUserIdAndItemId(Long userId, Long itemId);
}
