package com.example.skptemp.domain.item.service;

import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.item.repository.UserItemRepository;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Item> findItemListByUserId(Long userId) {
        List<UserItem> userItemList = userItemRepository.findByUserId(userId);
        return itemRepository.findByItemIdIn(userItemList.stream()
                .map(UserItem::getItemId)
                .toList());
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

    @Transactional(readOnly = false)
    @Override
    public void transferItem(Long userId, Long friendId, Long itemId, Long count) {
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


}
