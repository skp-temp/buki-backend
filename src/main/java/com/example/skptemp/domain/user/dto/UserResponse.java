package com.example.skptemp.domain.user.dto;

import com.example.skptemp.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String code;
    private String authProviderId;
    private String authority;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;


    public UserResponse(User user){
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.code = user.getCode();
        this.authProviderId = user.getAuthProviderId();
        this.authority = user.getAuthority();
        this.createdAt = user.getCreatedAt();
        this.lastUpdatedAt = user.getLastUpdatedAt();
    }
}
