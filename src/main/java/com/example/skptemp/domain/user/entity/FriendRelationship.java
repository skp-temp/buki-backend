package com.example.skptemp.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
public class FriendRelationship {
    @Id @Column(name = "relationship_id")
    private Long id;
    private Long userA;
    private Long userB;

    protected FriendRelationship(){}
    private FriendRelationship(Long userA, Long userB){
        this.userA = userA;
        this.userB = userB;
    }
}
