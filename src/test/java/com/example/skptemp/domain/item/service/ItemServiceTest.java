package com.example.skptemp.domain.item.service;


import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceTest {

    @Autowired UserItemService userItemService;
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;

    final Long TEST_KAKAO_ID = 1L;
    @BeforeEach
    void beforeEach(){
        //given
        User user1 = userRepository.save(User.createUser(TEST_KAKAO_ID));
        User user2 = userRepository.save(User.createUser(TEST_KAKAO_ID));

        Item item1 = itemRepository.save(Item.create(0L, ItemType.CLOTHE));

        //when
        //then
    }
    @Test
    void 아이템_선물_테스트_성공(){

    }

    @Test
    void 아이템_선물_테스트_실패(){

    }
}