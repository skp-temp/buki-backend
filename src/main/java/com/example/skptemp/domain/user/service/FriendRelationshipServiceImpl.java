package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.entity.FriendRelationship;
import com.example.skptemp.domain.user.repository.FriendRelationshipRepository;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FriendRelationshipServiceImpl implements FriendRelationshipService {

    private final FriendRelationshipRepository friendRelationshipRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    @Override
    public void enrollFriendRelationship(Long userA, Long userB) {
        validateUserId(userA);
        validateUserId(userB);
        friendRelationshipRepository.save(FriendRelationship.createFriendRelationship(userA, userB));
        friendRelationshipRepository.save(FriendRelationship.createFriendRelationship(userB, userA));
    }

    @Override
    public List<FriendRelationship> findFriendRelationshipList(Long userId) {
        validateUserId(userId);
        // userA 기준으로 친구 관계를 리스트로 조회한다.
        return friendRelationshipRepository.findByUserA(userId);
    }

    private void validateUserId(Long userId){
        if(userRepository.findById(userId).isEmpty())
            throw new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION);
    }
}
