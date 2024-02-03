package com.example.skptemp.domain.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FriendResponse {
    private List<FriendResult> friendIds;
}
