package com.example.skptemp.domain.comment.entity;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @Id @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JoinColumn(name = "charm_id")
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Charm charm;
    private Long charmId;
//    @JoinColumn(name = "user_id")
//    @ManyToOne(fetch = FetchType.EAGER)
//    private User user;
    private Long userId;
//    @JoinColumn(name = "item_id")
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Item item;
    private Long itemId;
    private String content;
    private LocalDateTime createdAt;
}
