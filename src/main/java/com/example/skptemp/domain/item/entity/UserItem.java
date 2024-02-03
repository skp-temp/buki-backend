package com.example.skptemp.domain.item.entity;

import com.example.skptemp.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
public class UserItem {
    @Id @Column(name = "user_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;
    Long itmeId;
}
