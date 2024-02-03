package com.example.skptemp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SocialUserResponse {
    boolean success;
    SocialUserResult socialUserResult;
}
