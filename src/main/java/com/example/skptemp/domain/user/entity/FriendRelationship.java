package com.example.skptemp.domain.user.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class FriendRelationship extends BaseEntity {
    @Id @Column(name = "relationship_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userA;
    private Long userB;
    private boolean isValid;

    protected FriendRelationship(){}
    private FriendRelationship(Long userA, Long userB){
        this.userA = userA;
        this.userB = userB;
        this.isValid = true;
    }

    public static FriendRelationship createFriendRelationship(Long userA, Long userB){
        validate(userA);
        validate(userB);
        return new FriendRelationship(userA, userB);
    }

    public void delete(){
        this.isValid = false;
    }

    private static void validate(Long userId){
        if(userId == null) throw new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION);
    }
}
