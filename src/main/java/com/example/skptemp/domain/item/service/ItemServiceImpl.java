package com.example.skptemp.domain.item.service;

import com.example.skptemp.domain.charm.repository.CharmRepository;
import com.example.skptemp.domain.item.dto.CheerItemResponse;
import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.dto.GiveItemRequest;
import com.example.skptemp.domain.item.dto.UserItemResult;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.item.repository.UserItemRepository;
import com.example.skptemp.global.common.SecurityUtil;
import com.example.skptemp.global.constant.Category;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemServiceImpl implements ItemService {
    public static final Random RANDOM = new Random();
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    private final UserItemService userItemService;
    private final CharmRepository charmRepository;


    @Override
    public List<CheerItemResponse> getCheerItemList(Long charmId) {
        Category category = charmRepository.findById(charmId).orElseThrow().getCategory();

        return userItemRepository.getCheerItemList(SecurityUtil.getUserId(), category);

    }

    @Override
    public GetUserItemResponse findItemListByUserId(Long userId) {
        List<UserItem> userItemList = userItemRepository.getUserItemList(userId);

        List<UserItemResult> userItemResultList = new ArrayList<>();

        for(UserItem userItem : userItemList){
            Item findItem = userItem.getItem();
            UserItemResult userItemResult =
                    UserItemResult.builder()
                            .itemType(findItem.getItemType().ordinal())
                            .itemName(findItem.getItemName())
                            .count(userItem.getCount())
                            .itemId(findItem.getId())
                            .categoryId(findItem.getCategory().ordinal())
                            .build();
            userItemResultList.add(userItemResult);
        }

        return GetUserItemResponse.builder()
                .userId(userId)
                .result(userItemResultList)
                .build();
    }

    @Override
    public int getItemCount(Long userId, Long itemId) {
        Optional<UserItem> userItemOpt = userItemService.findByUserIdAndItemId(userId, itemId);

        if(userItemOpt.isEmpty()) throw new GlobalException(GlobalErrorCode.ITEM_VALID_EXCEPTION);
        return userItemOpt.get().getCount();
    }

    @Override
    public int getTotalItemCount(Long userId) {
        List<UserItem> userItem = userItemRepository.findByUserId(userId);
        return userItem.size();
    }

    @Transactional
    @Override
    public void transferItem(Long userId, Long friendId, Long itemId, int count) {
        UserItem userItem = userItemService.getByUserIdAndItemId(userId, itemId);
        userItem.removeItem(count);

        Optional<UserItem> friendUserItemOpt = userItemService.findByUserIdAndItemId(friendId, itemId);

        if(friendUserItemOpt.isEmpty()){
            UserItem friendUserItem = userItemService.createUserItem(friendId, itemId, count);
            friendUserItem.addItem(count);
            return;
        }

        UserItem friendUserItem = friendUserItemOpt.get();
        friendUserItem.addItem(count);
    }

    @Transactional
    @Override
    public void giveItem(GiveItemRequest request) {
        Optional<UserItem> userItemOpt = userItemService.findByUserIdAndItemId(request.userId(), request.itemId());

        if(userItemOpt.isEmpty()){
            userItemService.createUserItem(request.userId(), request.itemId(), request.count());
            return;
        }
        UserItem userItem = userItemOpt.get();
        userItem.addItem(1);
    }
    @Transactional
    @Override
    public Long gacha(){
        Long itemId = getRandomItemId();
        Long userId = SecurityUtil.getUserId();
        userItemService.createUserItem(userId, itemId, 1);
        return itemId;
    }

    private Long getRandomItemId(){
        long count = itemRepository.count();
        if(count == 0){
            throw new GlobalException(GlobalErrorCode.OTHER);
        }

        Random random = RANDOM;
        return random.nextLong(count) + 1;
    }
}
