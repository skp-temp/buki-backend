package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.FriendResult;
import com.example.skptemp.domain.user.entity.FriendRelationship;

import java.util.List;

public interface FriendRelationshipService {
    void enrollFriendRelationship(Long userA, Long userB);
    List<FriendResult> findFriendRelationshipList(Long userId);
    void deleteFriendRelationship(Long userId, Long friendId);
}
