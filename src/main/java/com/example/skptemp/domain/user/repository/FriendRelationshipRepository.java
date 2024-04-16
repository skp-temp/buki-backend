package com.example.skptemp.domain.user.repository;

import com.example.skptemp.domain.user.entity.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long> {
    Optional<FriendRelationship> findByIdAndIsValidIsTrue(Long id);
    List<FriendRelationship> findByUserAAndIsValidIsTrue(Long userA);
    Optional<FriendRelationship> findByUserAAndUserBAndIsValidIsTrue(Long userA, Long userB);
}
