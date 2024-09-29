package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.charm.entity.Charm;
import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.notification.dto.NotificationEventRequest;
import com.example.skptemp.domain.notification.dto.NotificationType;
import com.example.skptemp.domain.notification.entity.NotificationEntity;
import com.example.skptemp.domain.notification.event.EventPublisher;
import com.example.skptemp.domain.notification.repository.NotificationRepository;
import com.example.skptemp.domain.user.dto.FriendResult;
import com.example.skptemp.domain.user.entity.FriendRelationship;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.FriendRelationshipRepository;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.common.SecurityUtil;
import com.example.skptemp.global.constant.BukiConstant;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.skptemp.domain.notification.util.NotificationUtil.*;

@Transactional
@RequiredArgsConstructor
@Service
public class FriendRelationshipService {

    private final FriendRelationshipRepository friendRelationshipRepository;
    private final EventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final CharmRepository charmRepository;
    private final NotificationRepository notificationRepository;

    public void sendFriendRequest(User friendUser) {
        String myName = userRepository.findById(SecurityUtil.getUserId()).orElseThrow().getFullName();
        eventPublisher.publishNotification(new NotificationEventRequest(
                FRIEND_REQUESTED_FORMAT,
                FRIEND_REQUESTED_MESSAGE_FORMAT.formatted(friendUser.getFullName()),
                "친구맺기",
                NotificationType.FRIEND,
                friendUser.getId(),
                myName
        ));

    }

    @Transactional
    public void enrollFriendRelationship(Long userA, Long userB, Long notificationId) {
        validateUserId(userA, userB);
        validateNotFriend(userA, userB);

        // 쌍방으로 친구 관계를 저장한다.
        friendRelationshipRepository.save(FriendRelationship.createFriendRelationship(userA, userB));
        friendRelationshipRepository.save(FriendRelationship.createFriendRelationship(userB, userA));
        User userAEntity = userRepository.findById(userA).orElseThrow();


        NotificationEntity notificationEntity = notificationRepository.findById(notificationId).orElseThrow();
        notificationEntity.setAccepted(true);

        NotificationEventRequest notificationEventRequest = new NotificationEventRequest(
                FRIEND_ACCEPTED_FORMAT,
                FRIEND_ACCEPTED_FORMAT,
                "친구 맺기",
                NotificationType.FRIEND,
                userB,
                userAEntity.getFullName()
        );
        eventPublisher.publishNotification(notificationEventRequest);
    }

    public List<FriendResult> findFriendList(Long userId) {
        validateUserId(userId);

        List<FriendResult> friendResultList = new ArrayList<>();

        // userA 기준으로 친구 관계를 리스트로 조회한다.
        List<FriendRelationship> friendList = friendRelationshipRepository.findByUserA(userId);
        List<Long> friendIdList = friendList.stream().map(FriendRelationship::getUserB).toList();

        List<User> friendInfoList = userRepository.findByIdInAndIsValidIsTrue(friendIdList);
        Map<Long, FriendResult> friendResultMap = new HashMap<>();

        // 친구의 사용자 정보를 담은 result 객체를 미리 만든다.
        for(var friend : friendInfoList) {
            FriendResult result = FriendResult.builder()
                    .friendId(friend.getId())
                    .firstName(friend.getFirstName())
                    .lastName(friend.getLastName())
                    .profileBadge(friend.getProfileBadge())
                    .set(new HashSet<>())
                    .build();
            friendResultList.add(result);

            friendResultMap.put(friend.getId(), result);
        }

        // 진행 중인 부적 category 정보를 찾는다.
        List<Charm> allCharmFriendsOwn = charmRepository.findByUserIdInAndIsValidIsTrue(friendIdList);

        for(var charm : allCharmFriendsOwn) {
            //TODO: 현재 만들고 있는 부적을 구분하기 위한 수단이 필요하다. (일단 레벨로만 판단한다.)
            // 부키의 부적은 21 레벨이 되어도 완성 상태가 아닐 수 있어, 별도 필드를 통한 상태 구분이 필수적이다.
            if(charm.getCharmLevel() == BukiConstant.CHARM_FINAL_COMPLETE_DAY)
                continue;

            FriendResult friendResult = friendResultMap.get(charm.getUserId());
            friendResult.getCategorySet().add(charm.getCategory());
        }

        return friendResultList;
    }
    @Transactional
    public void deleteFriendRelationship(Long userId, Long friendId) {
        validateUserId(userId, friendId);
        validateFriendRelationship(userId, friendId);

        FriendRelationship relationship1 = friendRelationshipRepository.findByUserAAndUserB(userId, friendId).get();
        FriendRelationship relationship2 = friendRelationshipRepository.findByUserAAndUserB(friendId, userId).get();

        friendRelationshipRepository.delete(relationship1);
        friendRelationshipRepository.delete(relationship2);
    }

    private void validateNotFriend(Long userId, Long friendId){
        if(friendRelationshipRepository.findByUserAAndUserB(userId, friendId).isPresent())
            throw new GlobalException(GlobalErrorCode.FRIEND_RELATIONSHIP_ALREADY_EXIST);
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
            throw new GlobalException("요청자의 사용자 코드를 이용한 친구 추가 요청입니다.", GlobalErrorCode.FRIEND_RELATIONSHIP_VALID_EXCEPTION);
        validateUserId(userA);
        validateUserId(userB);
    }
}
