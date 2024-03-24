package com.example.skptemp.domain.item.service;


import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.constant.LoginType;
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

    private static final LoginType TEST_LOGIN_TYPE = LoginType.KAKAO;
    private static final String TEST_AUTH_PROVIDER_ID = "123456789";
    @BeforeEach
    void beforeEach(){
        //given
        User user1 = userRepository.save(User.createUser(TEST_LOGIN_TYPE, TEST_AUTH_PROVIDER_ID));
        User user2 = userRepository.save(User.createUser(TEST_LOGIN_TYPE, TEST_AUTH_PROVIDER_ID));

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