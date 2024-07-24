package com.example.skptemp.domain.cheer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CheerItemEventRequest {

    private Long fromUserId;
    private Long toUserId;

    private Long itemId;

}
