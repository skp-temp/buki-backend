package com.example.skptemp.domain.user.dto;

import lombok.Getter;

@Getter
public class UserChangeNameRequest {
    Long userId;
    String firstName;
    String lastName;
}
