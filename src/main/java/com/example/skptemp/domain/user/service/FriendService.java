package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.entity.FriendRelationship;
import com.example.skptemp.domain.user.repository.FriendRelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRelationshipRepository friendRelationshipRepository;
    public FriendRelationship save(Long userA, Long userB) {
        return friendRelationshipRepository.save(FriendRelationship.createFriendRelationship(userA, userB));
    }
}
