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

    @Override
    public List<Item> findItemListByUserId(Long userId) {
        List<UserItem> userItemList = userItemRepository.findByUserId(userId);
        return itemRepository.findByItemIdIn(userItemList.stream()
                .map(UserItem::getItemId)
                .toList());
    }

    @Override
    public int itemCount(Long userId, Long itemId) {
        Optional<UserItem> userItemOpt = userItemRepository.findByUserIdAndItemId(userId, itemId);

        if(userItemOpt.isEmpty()) throw new GlobalException(GlobalErrorCode.ITEM_VALID_EXCEPTION);
        return userItemOpt.get().getCount();
    }
}
