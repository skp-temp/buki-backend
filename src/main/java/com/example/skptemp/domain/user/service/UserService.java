package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.global.constant.LoginType;

public interface UserService {
    void logout();
    LoginResponse doLogin(LoginType loginType, String authProviderId, String pushToken);
    SignUpResponse doSignup(SignupRequest request);
    UserResponse findByUserId(Long id);
    User findByLoginTypeAndAuthProviderId(LoginType loginType, String platformProviderId);
    User findByCode(String code);
    String createJwt(Long id);
    String createTestJwt();
    UserResponse changeUserName(UserChangeNameRequest request);
    void deleteUser(Long id);
    GetGachaStatusResponse getGachaStatus(Long id);
    void changeProfileBadge(Long id, Badge badge);

    boolean checkTokenValid();
}
