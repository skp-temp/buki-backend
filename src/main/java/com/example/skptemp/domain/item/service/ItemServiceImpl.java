package com.example.skptemp.domain.item.service;

import com.example.skptemp.domain.item.dto.GetUserItemResponse;
import com.example.skptemp.domain.item.dto.GiveItemRequest;
import com.example.skptemp.domain.item.dto.UserItemResult;
import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.item.repository.UserItemRepository;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    private final UserItemService userItemService;



    @Override
    public GetUserItemResponse findItemListByUserId(Long userId) {
        List<UserItem> userItemList = userItemRepository.findByUserId(userId);
        List<Item> itemList = itemRepository.findByIdIn(userItemList.stream()
                .map(UserItem::getItemId)
                .toList());

        List<UserItemResult> userItemResultList = new ArrayList<>();

        for(UserItem userItem : userItemList){
            Item findItem = itemList.stream()
                    .filter((Item item) -> item.getId().equals(userItem.getItemId()))
                    .findAny()
                    .get();


            UserItemResult userItemResult =
                    UserItemResult.builder()
                            .itemType(findItem.getItemType())
                            .itemName(findItem.getItemName())
                            .count(userItem.getCount())
                            .itemId(findItem.getId())
                            .category(findItem.getCategory())
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
}
