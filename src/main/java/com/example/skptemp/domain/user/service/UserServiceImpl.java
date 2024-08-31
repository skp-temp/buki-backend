package com.example.skptemp.domain.user.service;

import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.badge.entity.UserBadge;
import com.example.skptemp.domain.badge.repository.UserBadgeRepository;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public LoginResponse doLogin(LoginType loginType, String platformProviderId, String pushToken) {
        User user = findByLoginTypeAndAuthProviderId(loginType, platformProviderId);
        user.changePushToken(pushToken);

        String jwt = createJwt(user.getId());
        return new LoginResponse(loginType, platformProviderId, user.getPushToken(), jwt);
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

        boolean saved = false;
        while(!saved) {
            try {
                userRepository.save(user);
                saved = true;
            } catch (DataIntegrityViolationException e) {
                System.out.println("unique constraint violation for friend code field");
                user.renewFriendCode();
                userRepository.save(user); // 친구 코드 중복 발급 시 재발급 처리 한다. (다시 발급했을 때에도 중복된 경우에 대한 대비 필요)
            }
        }
        User findUser = findByLoginTypeAndAuthProviderId(loginType, platformProviderId);
        String jwt = createJwt(findUser.getId());

        return new SignUpResponse(loginType, platformProviderId, jwt);
    }

    @Override
    public UserResponse findByUserId(Long id) {
        assertUserId(id);
        User user = findById(id);
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
        User user = findById(id);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        return jwtProvider.createDefaultToken(userDetails);
    }

    @Override
    public String createTestJwt() {
        User user = findById(GlobalConstants.TEST_ACCOUNT_ID);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        return jwtProvider.createTestToken(userDetails);
    }

    @Transactional
    @Override
    public UserResponse changeUserName(UserChangeNameRequest request) {
        assertUserId(request.getUserId());
        User user = findById(request.getUserId());
        user.changeName(request.getFirstName(), request.getLastName());
        return new UserResponse(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id){
        assertUserId(id);
        User user = findById(id);
        user.deleteUser();
    }
    @Override
    public GetGachaStatusResponse getGachaStatus(Long id) {
        User findUser = userRepository.findByIdAndIsValidIsTrue(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
        return new GetGachaStatusResponse(findUser.isGachaEnable());
    }

    @Override
    public void changeProfileBadge(Long id, Badge badge){
        User findUser = findById(id);
        assertValidBadge(id, badge);

        findUser.changeProfileBadge(badge);
    }

    private void assertValidBadge(Long id, Badge badge){
        List<UserBadge> userBadgeList = userBadgeRepository.findByUserIdAndIsValidIsTrue(id);

        Optional<UserBadge> userBadge1 = userBadgeList.stream().filter(userBadge -> userBadge.getBadge().equals(badge)).findAny();
        if(userBadge1.isEmpty()){
            throw new GlobalException(GlobalErrorCode.USER_NO_BADGE_EXCEPTION);
        }
    }


    private User findById(Long id){
        return userRepository.findByIdAndIsValidIsTrue(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_VALID_EXCEPTION));
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
