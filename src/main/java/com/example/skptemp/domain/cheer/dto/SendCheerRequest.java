package com.example.skptemp.domain.cheer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendCheerRequest {
    private String message;

    private Long charmId;

    private Long itemId;

    private Long userId;
}
