package com.example.skptemp.domain.user.dto;

import com.example.skptemp.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String code;
    private Long point;
    private Long kakaoId;
    private String authority;

    public UserResponse(User user){
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.code = user.getCode();
        this.point = user.getPoint();
        this.kakaoId = user.getKakaoId();
        this.authority = user.getAuthority();
    }
}
