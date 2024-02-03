package com.example.skptemp.domain.badge.entity;

import com.example.skptemp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class UserBadge {
    @Id @Column(name = "user_badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "badge_id")
//    private Badge badge;
    private Long badgeId;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User user;
    private Long userId;

    protected UserBadge(){}
}
