package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.entity.FriendRelationship;

import java.util.List;

public interface FriendRelationshipService {
    void enrollFriendRelationship(Long userA, Long userB);
    List<FriendRelationship> findFriendRelationshipList(Long userId);
}
