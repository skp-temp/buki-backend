package com.example.skptemp.domain.item.service;

import com.example.skptemp.domain.item.entity.Item;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.domain.item.repository.ItemRepository;
import com.example.skptemp.domain.item.repository.UserItemRepository;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserItemServiceImpl implements UserItemService{
    private final UserItemRepository userItemRepository;
    private final ItemRepository itemRepository;
    private final EntityManager entityManager;
    @Override
    public Optional<UserItem> findByUserIdAndItemId(Long userId, Long itemId) {
        return userItemRepository.findByUserIdAndItemId(userId, itemId); // find: 존재하지 않는 경우 존재
    }

    @Override
    public UserItem getByUserIdAndItemId(Long userId, Long itemId) { // get: 객체 존재하지 않는 경우 exception throw
        Optional<UserItem> userItemOpt = findByUserIdAndItemId(userId, itemId);
        if(userItemOpt.isEmpty()) throw new GlobalException(GlobalErrorCode.ITEM_VALID_EXCEPTION);

        return userItemOpt.get();
    }

    @Transactional
    @Override
    public UserItem createUserItem(Long userId, Long itemId, int count) {
        Item item = entityManager.getReference(Item.class, itemId);
        UserItem userItem = UserItem.create(userId, item);
        userItem.addItem(count);
        userItemRepository.save(userItem);
        return userItem;
    }
}
