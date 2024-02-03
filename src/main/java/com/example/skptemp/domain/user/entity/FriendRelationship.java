package com.example.skptemp.domain.user.entity;

import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Builder;
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

    public static FriendRelationship createFriendRelationship(Long userA, Long userB){
        validate(userA);
        validate(userB);
        return new FriendRelationship(userA, userB);
    }

    private static void validate(Long userId){
        if(userId == null) throw new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION);
    }
}
