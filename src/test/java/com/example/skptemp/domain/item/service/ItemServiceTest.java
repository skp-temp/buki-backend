package com.example.skptemp.domain.item.service;


import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.dto.GiveItemRequest;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.ItemType;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.constant.Category;
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
    private String profileImg = "profile_img";
    @BeforeEach
    void beforeEach(){

    }

    @Test
    void 사용자_아이템_조회(){
        //given
        User user1 = userRepository.save(User.createUser(TEST_LOGIN_TYPE, TEST_AUTH_PROVIDER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PUSH_TOKEN, profileImg));

        Item 테스트_아이템1 = itemRepository.save(Item.create(Category.BEAUTY, "테스트 아이템", ItemType.CLOTHE));
        Item 테스트_아이템2 = itemRepository.save(Item.create(Category.DIET, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템3 = itemRepository.save(Item.create(Category.DIET, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템4 = itemRepository.save(Item.create(Category.DIET, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템5 = itemRepository.save(Item.create(Category.DIET, "테스트 아이템", ItemType.EQUIPMENT));
        Item 테스트_아이템6 = itemRepository.save(Item.create(Category.DIET, "테스트 아이템", ItemType.EQUIPMENT));

        //when
        itemService.giveItem(new GiveItemRequest(user1.getId(), 테스트_아이템1.getId(), 2));
        itemService.giveItem(new GiveItemRequest(user1.getId(), 테스트_아이템2.getId(), 1));
        itemService.giveItem(new GiveItemRequest(user1.getId(), 테스트_아이템3.getId(), 1));
        itemService.giveItem(new GiveItemRequest(user1.getId(), 테스트_아이템4.getId(), 1));
        itemService.giveItem(new GiveItemRequest(user1.getId(), 테스트_아이템5.getId(), 1));
        itemService.giveItem(new GiveItemRequest(user1.getId(), 테스트_아이템6.getId(), 1));

        //then
        GetUserItemResponse response = itemService.findItemListByUserId(user1.getId());
        Assertions.assertThat(response.getResult().size()).isEqualTo(6);
    }

    @Test
    void 아이템_선물_테스트_성공(){

    }

    @Test
    void 아이템_선물_테스트_실패(){

    }
}