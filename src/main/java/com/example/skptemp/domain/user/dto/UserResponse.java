package com.example.skptemp.domain.user.dto;

import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.global.constant.LoginType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private Long userId;
    private LoginType loginType;
    private String firstName;
    private String lastName;
    private String code;
    private String authority;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private String pushToken;


    public UserResponse(User user){
        this.userId = user.getId();
        this.loginType = user.getLoginType();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.code = user.getCode();
        this.authority = user.getAuthority();
        this.createdAt = user.getCreatedAt();
        this.lastUpdatedAt = user.getLastUpdatedAt();
        this.pushToken = user.getPushToken();
    }
}
