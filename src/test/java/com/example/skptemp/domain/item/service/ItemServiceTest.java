package com.example.skptemp.domain.item.service;


import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.constant.LoginType;
import org.assertj.core.api.Assertions;
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
    private static final String TEST_FIRST_NAME = "강";
    private static final String TEST_LAST_NAME = "동훈";
    private static final String TEST_PUSH_TOKEN = "..";
    @BeforeEach
    void beforeEach(){

    }

    @Test
    void 사용자_아이템_조회(){
        //given
        User user1 = userRepository.save(User.createUser(TEST_LOGIN_TYPE, TEST_AUTH_PROVIDER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PUSH_TOKEN));

        Item 테스트_아이템1 = itemRepository.save(Item.create(0L, "테스트 아이템", ItemType.CLOTHE));
        Item 테스트_아이템2 = itemRepository.save(Item.create(1L, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템3 = itemRepository.save(Item.create(1L, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템4 = itemRepository.save(Item.create(1L, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템5 = itemRepository.save(Item.create(1L, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템6 = itemRepository.save(Item.create(1L, "테스트 아이템", ItemType.EQUIPMENT));

        //when
        itemService.giveItemToUser(테스트_아이템1.getId(), user1.getId());
        itemService.giveItemToUser(테스트_아이템1.getId(), user1.getId());
        itemService.giveItemToUser(테스트_아이템2.getId(), user1.getId());
        itemService.giveItemToUser(테스트_아이템3.getId(), user1.getId());
        itemService.giveItemToUser(테스트_아이템4.getId(), user1.getId());
        itemService.giveItemToUser(테스트_아이템5.getId(), user1.getId());
        itemService.giveItemToUser(테스트_아이템6.getId(), user1.getId());

        //then
        GetUserItemResponse response = itemService.findItemListByUserId(user1.getId());
        Assertions.assertThat(response.getResult().size()).isEqualTo(7);
    }

    @Test
    void 아이템_선물_테스트_성공(){

    }

    @Test
    void 아이템_선물_테스트_실패(){

    }
}