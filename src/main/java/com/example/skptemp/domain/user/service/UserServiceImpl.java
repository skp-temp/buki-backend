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

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponse doLogin(LoginType loginType, String platformProviderId, String jwt) {
        findByLoginTypeAndAuthProviderId(loginType, platformProviderId);

        return new LoginResponse(loginType, platformProviderId, jwt);
    }
    @Transactional
    @Override
    public SignUpResponse doSignup(SignupRequest request) {
        LoginType loginType = request.loginType();
        String platformProviderId = request.platformProviderId();
        String firstName = request.firstName();
        String lastName = request.lastName();
        String pushToken = request.pushToken();

        User user = User.createUser(loginType, platformProviderId, firstName, lastName, pushToken);

        assertDuplicateUser(loginType, platformProviderId);
        userRepository.save(user);

        User findUser = findByLoginTypeAndAuthProviderId(loginType, platformProviderId);
        String jwt = createJwt(findUser.getId());

        return new SignUpResponse(loginType, platformProviderId, jwt);
    }

    @Override
    public UserResponse findById(Long id) {
        assertUserId(id);
        User user = userRepository.findByIdAndIsValidIsTrue(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
        return new UserResponse(user);
    }


    @Override
    public User findByLoginTypeAndAuthProviderId(LoginType loginType, String platformProviderId){
        return userRepository.findByLoginTypeAndPlatformProviderIdAndIsValidIsTrue(loginType, platformProviderId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
    }


    @Override
    public User findByCode(String code) {
        return userRepository.findByCodeAndIsValidIsTrue(code).orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
    }

    @Override
    public String createJwt(Long id){
        assertUserId(id);
        User user = userRepository.findByIdAndIsValidIsTrue(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return jwtProvider.createDefaultToken(userDetails);
    }

    @Override
    public String createTestJwt() {
        User user = userRepository.findByIdAndIsValidIsTrue(GlobalConstants.TEST_ACCOUNT_ID)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.TEST_ACCOUNT_INVALID));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return jwtProvider.createTestToken(userDetails);
    }

    @Transactional
    @Override
    public UserResponse changeUserName(UserChangeNameRequest request) {
        assertUserId(request.getUserId());
        User user = userRepository.findByIdAndIsValidIsTrue(request.getUserId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
        user.changeName(request.getFirstName(), request.getLastName());
        return new UserResponse(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id){
        assertUserId(id);
        User user = userRepository.findByIdAndIsValidIsTrue(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
        user.deleteUser();
    }

    @Override
    public void validateUserOrThrow(Long id){
        assertUserId(id);
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));

        if(!findUser.isValid())
            throw new GlobalException(GlobalErrorCode.USER_DELETED_EXCEPTION);
    }

    private void assertDuplicateUser(LoginType loginType, String platformProviderId){
        if(userRepository.findByLoginTypeAndPlatformProviderIdAndIsValidIsTrue(loginType, platformProviderId).isPresent()){
            throw new GlobalException(GlobalErrorCode.USER_CONFLICT);
        }
    }

    private void assertUserId(Long userId){
        if(userId == null){
            throw new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION);
        }
    }
}
