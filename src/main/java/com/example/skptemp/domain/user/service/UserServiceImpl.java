package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.user.dto.*;
import com.example.skptemp.domain.user.entity.User;
import com.example.skptemp.domain.user.entity.UserDetailsImpl;
import com.example.skptemp.domain.user.repository.UserRepository;
import com.example.skptemp.global.configuration.JwtProvider;
import com.example.skptemp.global.constant.LoginType;
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
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService{
    //private final LoginService loginService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    //TODO: login, signup 연동 플랫폼 상관 없도록 구현 변경해야
    @Override
    public LoginResponse doLogin(LoginType loginType, String authProviderId, String jwt) {
//        SocialAuthResponse authResponse = loginService.getAccessToken(token);
//        log.info("service auth response token: {}", authResponse.getAccessToken());
//        SocialUserResult userResult = loginService.getUserInfo(authResponse.getAccessToken());
//
//        Long authProviderId = userResult.getId();
//
//        Optional<User> userOpt = userRepository.findByAuthProviderId(authProviderId);
//
//        if(userOpt.isEmpty())
//            return new SocialUserResponse(false, null);
        Optional<User> findUser = userRepository.findByLoginTypeAndAuthProviderId(loginType, authProviderId);
        findUser.orElseThrow(() -> new GlobalException("존재 하지 않는 사용자 입니다.", GlobalErrorCode.USER_VALID_EXCEPTION));

        return new LoginResponse(loginType, authProviderId, jwt);
    }
    @Transactional
    @Override
    public SignUpResponse doSignup(LoginType loginType, String authProviderId) {
//        SocialAuthResponse authResponse = loginService.getAccessToken(token);
//        SocialUserResult userResult = loginService.getUserInfo(authResponse.getAccessToken());
//        Long authProviderId = userResult.getId();
        User user = User.createUser(loginType, authProviderId);

        assertDuplicateUser(loginType, authProviderId);
        userRepository.save(user);

        User findUser = findByLoginTypeAndAuthProviderId(loginType, authProviderId);
        String jwt = createJwt(findUser.getId());

        return new SignUpResponse(loginType, authProviderId, jwt);
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
        return new UserResponse(user);
    }


    @Override
    public User findByLoginTypeAndAuthProviderId(LoginType loginType, String authProviderId){
        return userRepository.findByLoginTypeAndAuthProviderId(loginType, authProviderId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
    }


    @Override
    public User findByCode(String code) {
        return userRepository.findByCode(code).orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
    }

    @Override
    public String createJwt(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(""));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        return jwtProvider.createToken(userDetails, GlobalConstants.TOKEN_EXPIRATION_TIME);
    }

    @Transactional
    @Override
    public UserResponse changeUserName(UserChangeNameRequest request) {
        assertUserId(request.getUserId());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
        user.changeName(request.getFirstName(), request.getLastName());
        return new UserResponse(user);
    }

    private void assertDuplicateUser(LoginType loginType, String authProviderId){
        if(userRepository.findByLoginTypeAndAuthProviderId(loginType, authProviderId).isPresent()){
            throw new GlobalException(GlobalErrorCode.USER_CONFLICT);
        }
    }

    private void assertUserId(Long userId){
        if(userId == null){
            throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
        }
    }
}
