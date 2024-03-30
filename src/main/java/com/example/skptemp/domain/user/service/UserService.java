package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.LoginResponse;
import com.example.skptemp.domain.user.dto.SignUpResponse;
import com.example.skptemp.domain.user.dto.UserChangeNameRequest;
import com.example.skptemp.domain.user.dto.UserResponse;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.global.constant.LoginType;

public interface UserService {
    LoginResponse doLogin(LoginType loginType, String authProviderId, String jwt);
    SignUpResponse doSignup(LoginType loginType, String authProviderId);
    UserResponse findById(Long id);
    User findByLoginTypeAndAuthProviderId(LoginType loginType, String authProviderId);
    User findByCode(String code);
    String createJwt(Long id);
    UserResponse changeUserName(UserChangeNameRequest request);

}
