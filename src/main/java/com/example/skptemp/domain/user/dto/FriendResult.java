package com.example.skptemp.domain.user.dto;

import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.global.constant.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
public class FriendResult {
    private final Long friendId;
    private final String firstName;
    private final String lastName;
    @Schema(description = "대표 뱃지 id", example = "1")
    private final int profileBadgeId;
    @Schema(description = "현재 만들고 있는 부적 카테고리 정보")
    private Set<Category> categorySet;

    @Builder
    public FriendResult(Long friendId, String firstName, String lastName, Badge profileBadge, Set<Category> set){
        this.friendId = friendId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileBadgeId = profileBadge != null ? profileBadge.getId() : 0;
        this.categorySet = set;
    }
}
