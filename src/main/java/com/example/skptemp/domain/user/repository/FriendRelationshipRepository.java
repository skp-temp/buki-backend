package com.example.skptemp.domain.user.repository;

import com.example.skptemp.domain.user.entity.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long> {
    List<FriendRelationship> findByUserA(Long userA);
    Optional<FriendRelationship> findByUserAAndUserB(Long userA, Long userB);
}
