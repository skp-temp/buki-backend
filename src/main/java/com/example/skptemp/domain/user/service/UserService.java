package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.global.constant.LoginType;

public interface UserService {
    LoginResponse doLogin(LoginType loginType, String authProviderId);
    SignUpResponse doSignup(SignupRequest request);
    UserResponse findById(Long id);
    User findByLoginTypeAndAuthProviderId(LoginType loginType, String platformProviderId);
    User findByCode(String code);
    String createJwt(Long id);
    String createTestJwt();
    UserResponse changeUserName(UserChangeNameRequest request);
    void deleteUser(Long id);
    void validateUserOrThrow(Long id);
}
