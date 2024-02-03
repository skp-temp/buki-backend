package com.example.skptemp.domain.user.dto;

import com.example.skptemp.domain.user.entity.FriendRelationship;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendResult {
    Long friendId;

    public FriendResult(FriendRelationship friendRelationship){
        this.friendId = friendRelationship.getUserB();
    }
}
