package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.FriendResult;
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

    @Transactional
    @Override
    public void enrollFriendRelationship(Long userA, Long userB) {
        validateUserId(userA, userB);
        // 쌍방으로 친구 관계를 저장한다.
        friendRelationshipRepository.save(FriendRelationship.createFriendRelationship(userA, userB));
        friendRelationshipRepository.save(FriendRelationship.createFriendRelationship(userB, userA));
    }

    @Override
    public List<FriendResult> findFriendRelationshipList(Long userId) {
        validateUserId(userId);
        // userA 기준으로 친구 관계를 리스트로 조회한다.
        return friendRelationshipRepository.findByUserA(userId).stream()
                .map(FriendResult::new)
                .toList();
    }
    @Transactional
    @Override
    public void deleteFriendRelationship(Long userId, Long friendId) {
        validateUserId(userId, friendId);
        validateFriendRelationship(userId, friendId);

        FriendRelationship relationship1 = friendRelationshipRepository.findByUserAAndUserB(userId, friendId).get();
        FriendRelationship relationship2 = friendRelationshipRepository.findByUserAAndUserB(friendId, userId).get();

        friendRelationshipRepository.delete(relationship1);
        friendRelationshipRepository.delete(relationship2);
    }

    @Override
    public int getFriendsCount(Long userId) {
        return friendRelationshipRepository.findByUserA(userId).size();
    }

    private void validateFriendRelationship(Long userId, Long friendId){
        if(friendRelationshipRepository.findByUserAAndUserB(userId, friendId).isEmpty())
            throw new GlobalException(GlobalErrorCode.FRIEND_RELATIONSHIP_VALID_EXCEPTION);
    }

    private void validateUserId(Long userId){
        if(userRepository.findById(userId).isEmpty())
            throw new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION);
    }
    private void validateUserId(Long userA, Long userB){
        if(userA.equals(userB))
            throw new GlobalException(GlobalErrorCode.FRIEND_RELATIONSHIP_VALID_EXCEPTION);
        validateUserId(userA);
        validateUserId(userB);
    }
}
