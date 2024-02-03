package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.FriendResult;
import com.example.skptemp.domain.user.entity.FriendRelationship;
import com.example.skptemp.domain.user.repository.FriendRelationshipRepository;
import com.example.skptemp.global.error.GlobalException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendRelationshipServiceTest {

    @Autowired FriendRelationshipService friendRelationshipService;
    @Autowired FriendRelationshipRepository friendRelationshipRepository;

    final Long TEST_USER_ID1 = 1L;
    final Long TEST_USER_ID2 = 2L;
    final Long UNKNOWN_TEST_USER = 10000000L;

    @AfterEach
    void afterEach(){
        friendRelationshipRepository.deleteAll();
    }

    @Test
    void 친구_추가_성공(){
        //given
        //when
        friendRelationshipService.enrollFriendRelationship(TEST_USER_ID1, TEST_USER_ID2);
        //then
        assertThat(friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID1, TEST_USER_ID2)).isNotEmpty();
        assertThat(friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID2, TEST_USER_ID1)).isNotEmpty();
    }

    @Test
    void 친구_추가_실패(){
        //given
        //when
        //then
        assertThrows(GlobalException.class, () -> friendRelationshipService.enrollFriendRelationship(TEST_USER_ID1, UNKNOWN_TEST_USER));
    }

    @Test
    void 친구_삭제_성공(){
        //given
        friendRelationshipService.enrollFriendRelationship(TEST_USER_ID1, TEST_USER_ID2);

        //when
        friendRelationshipService.deleteFriendRelationship(TEST_USER_ID1, TEST_USER_ID2);

        //then
        Optional<FriendRelationship> findFriendRelationship = friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID1, TEST_USER_ID2);
        assertThat(findFriendRelationship).isEmpty();
        findFriendRelationship = friendRelationshipRepository.findByUserAAndUserB(TEST_USER_ID2, TEST_USER_ID1);
        assertThat(findFriendRelationship).isEmpty();
    }

    @Test
    void 친구_삭제_실패(){
        //given
        //when
        //then
        assertThrows(GlobalException.class,
                () -> friendRelationshipService.deleteFriendRelationship(TEST_USER_ID1, TEST_USER_ID2));
    }
}