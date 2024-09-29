//package com.example.skptemp.domain.user.service;
//
//import com.example.skptemp.common.TestConstants;
//import com.example.skptemp.domain.charm.entity.Charm;
//import com.example.skptemp.domain.charm.repository.CharmRepository;
//import com.example.skptemp.domain.notification.event.EventPublisher;
//import com.example.skptemp.domain.user.dto.FriendResult;
//import com.example.skptemp.domain.user.entity.FriendRelationship;
//import com.example.skptemp.domain.user.repository.FriendRelationshipRepository;
//import com.example.skptemp.global.constant.Category;
//import com.example.skptemp.global.error.GlobalException;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class FriendRelationshipServiceTest {
//
//    @Autowired FriendRelationshipService friendRelationshipService;
//    @Autowired FriendRelationshipRepository friendRelationshipRepository;
//    @Autowired CharmRepository charmRepository;
//    @MockBean
//    EventPublisher eventPublisher;
//
//    final Long TEST_USER_ID1 = 1L;
//    final Long TEST_USER_ID2 = 2L;
//    final Long UNKNOWN_TEST_USER = 10000000L;
//
//    @AfterEach
//    void afterEach(){
//        friendRelationshipRepository.deleteAll();
//    }
//
//    @Test
//    void 친구_추가_성공(){
//        //given
//        //when
//        friendRelationshipService.enrollFriendRelationship(TEST_USER_ID1, TEST_USER_ID2, request.getNotificationId());
//        //then
//        assertThat(friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID1, TEST_USER_ID2)).isNotEmpty();
//        assertThat(friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID2, TEST_USER_ID1)).isNotEmpty();
//    }
//
//    @Test
//    void 친구_추가_실패(){
//        //given
//        //when
//        //then
//        assertThrows(GlobalException.class, () -> friendRelationshipService.enrollFriendRelationship(TEST_USER_ID1, UNKNOWN_TEST_USER, request.getNotificationId()));
//    }
//
//    @Test
//    void 친구_중복_추가_실패(){
//        //given
//        //when
//        //then
//        assertThrows(GlobalException.class, () -> friendRelationshipService.enrollFriendRelationship(TEST_USER_ID1, TEST_USER_ID1, request.getNotificationId()));
//    }
//
//    @Test
//    void 친구_삭제_성공(){
//        //given
//        friendRelationshipService.enrollFriendRelationship(TEST_USER_ID1, TEST_USER_ID2, request.getNotificationId());
//
//        //when
//        friendRelationshipService.deleteFriendRelationship(TEST_USER_ID1, TEST_USER_ID2);
//
//        //then
//        Optional<FriendRelationship> findFriendRelationship = friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID1, TEST_USER_ID2);
//        assertThat(findFriendRelationship).isEmpty();
//        findFriendRelationship = friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID2, TEST_USER_ID1);
//        assertThat(findFriendRelationship).isEmpty();
//    }
//
//    @Test
//    void 친구_삭제_실패(){
//        //given
//        //when
//        //then
//        assertThrows(GlobalException.class,
//                () -> friendRelationshipService.deleteFriendRelationship(TEST_USER_ID1, TEST_USER_ID2));
//    }
//
//    @Test
//    void 친구_중복_삭제_실패(){
//        //given
//        //when
//        //then
//        assertThrows(GlobalException.class, () -> friendRelationshipService.deleteFriendRelationship(TEST_USER_ID1, TEST_USER_ID1));
//    }
//
//    @Test
//    void 친구_조회(){
//        //given
//        friendRelationshipService.enrollFriendRelationship(1L, 2L, request.getNotificationId());
//        friendRelationshipService.enrollFriendRelationship(1L, 3L, request.getNotificationId());
//        friendRelationshipService.enrollFriendRelationship(1L, 4L, request.getNotificationId());
//
//        creatTestCharm(2L, new Category[]{Category.DIET, Category.BEAUTY});
//        creatTestCharm(3L, new Category[]{Category.DIET, Category.BEAUTY, Category.HUSTLE});
//        creatTestCharm(4L, new Category[]{Category.DIET, Category.DIET, Category.BEAUTY, Category.HUSTLE, Category.HUSTLE});
//
//        //when
//        List<FriendResult> friendResultList = friendRelationshipService.findFriendList(TestConstants.TEST_USER_ID);
//
//        //then
//        for(var friend : friendResultList){
//            System.out.println(friend.toString());
//        }
//    }
//    private void creatTestCharm(Long userId, Category[] categoryArray){
//        for(var category : categoryArray){
//            charmRepository.save(Charm.builder()
//                    .userId(userId)
//                    .category(category)
//                    .alarmOn(false)
//                    .build());
//        }
//    }
//}