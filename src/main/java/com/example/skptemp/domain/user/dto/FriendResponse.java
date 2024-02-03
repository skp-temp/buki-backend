package com.example.skptemp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FriendResponse {
    private List<FriendResult> friendIds;
}
