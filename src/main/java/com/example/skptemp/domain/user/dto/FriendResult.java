package com.example.skptemp.domain.user.dto;

import com.example.skptemp.domain.badge.dto.BadgeDto;
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
    private BadgeDto badge;
    @Schema(description = "현재 만들고 있는 부적 카테고리 정보")
    private Set<Category> categorySet;
    private String profileImg;

    @Builder
    public FriendResult(Long friendId, String firstName, String lastName, BadgeDto badge, Set<Category> set,String profileImg){
        this.friendId = friendId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.badge = badge;
        this.categorySet = set;
        this.profileImg = profileImg;
    }
}
