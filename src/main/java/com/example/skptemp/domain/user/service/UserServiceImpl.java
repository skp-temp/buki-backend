package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.entity.UserDetailsImpl;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.configuration.JwtProvider;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import com.example.skptemp.global.util.GlobalConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService{
    private final LoginService loginService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public SocialUserResponse doSocialLogin(String token) {
        SocialAuthResponse authResponse = loginService.getAccessToken(token);
        log.info("service auth response token: {}", authResponse.getAccessToken());
        SocialUserResult userResult = loginService.getUserInfo(authResponse.getAccessToken());

        Long kakaoId = userResult.getId();
        // TODO: DB에 없는 유저의 경우 어떻게 처리할 지
        Optional<User> userOpt = userRepository.findByKakaoId(kakaoId);

        if(userOpt.isEmpty())
            return new SocialUserResponse(false, null);

        return new SocialUserResponse(true, userResult);
    }
    @Override
    public SocialUserResponse doSocialSignup(String token) {
        SocialAuthResponse authResponse = loginService.getAccessToken(token);
        SocialUserResult userResult = loginService.getUserInfo(authResponse.getAccessToken());
        Long userKakaoId = userResult.getId();
        User user = User.createUser(userKakaoId);

        assertDuplicateUser(userKakaoId);
        userRepository.save(user);

        return new SocialUserResponse(true, userResult);
    }
    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByKakaoId(Long kakaoId){
        return userRepository.findByKakaoId(kakaoId).orElseThrow();
    }

    @Override
    public String createJwt(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(""));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        return jwtProvider.createToken(userDetails, GlobalConstants.TOKEN_EXPIRATION_TIME);
    }

    @Override
    public UserResponse changeUserName(UserChangeNameRequest request) {
        User user = findById(request.getUserId());
        user.changeName(request.getFirstName(), request.getLastName());
        return new UserResponse(user);
    }

    private void assertDuplicateUser(Long kakaoId){
        if(userRepository.findByKakaoId(kakaoId).isPresent()){
            throw new GlobalException(GlobalErrorCode.USER_CONFLICT);
        }
    }
}
