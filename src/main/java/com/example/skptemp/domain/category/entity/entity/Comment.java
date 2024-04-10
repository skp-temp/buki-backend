package com.example.skptemp.domain.category.entity.entity;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {
    @Id @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long charmId;
    private Long userId;
    private Long itemId;
    private String content;
    private LocalDateTime createdAt;
}
