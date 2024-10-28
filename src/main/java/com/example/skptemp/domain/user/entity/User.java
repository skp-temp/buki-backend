package com.example.skptemp.domain.user.entity;

import com.example.skptemp.domain.badge.entity.Badge;
import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.domain.item.entity.UserItem;
import com.example.skptemp.global.constant.LoginType;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import com.example.skptemp.global.util.FriendCodeGenerator;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false, unique = true, length = FriendCodeGenerator.CODE_LENGTH)
    private String code; // 친구 추가 용도 발급 코드를 의미
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String pushToken;       // firebase 토큰
    @Enumerated(EnumType.STRING)
    private LoginType loginType;         // authentication 플랫폼 타입
    private String platformProviderId ;  // authentication 플랫폼 제공 identifier
    private boolean gachaEnable;    // 뽑기 상태
    private int gachaCount = 0;     // 누적 뽑기 횟수
    @Enumerated(EnumType.STRING)
    @Nullable
    private Badge profileBadge;     // 대표 뱃지
    private String authority;
    private boolean isValid;        // 논리적 삭제 처리를 위함
    private String profileImg;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserItem> userItemList;
    protected User(){
    }

    private User(String uuid, LoginType loginType, String platformProviderId, String firstName, String lastName, String authority, String pushToken){
        this.code = uuid;
        this.loginType = loginType;
        this.platformProviderId = platformProviderId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authority = authority;
        this.pushToken = pushToken;

        this.gachaEnable = true;
        this.gachaCount = 0;

        this.isValid = true;
    }

    public static User createUser(LoginType loginType, String platformProviderId, String firstName, String lastName, String pushToken, String profileImg){
        String uuid = FriendCodeGenerator.generateRandomCode();
        return new User(uuid, loginType, platformProviderId, firstName, lastName, "USER", pushToken);
    }
    public void renewFriendCode(){
        this.code = FriendCodeGenerator.generateRandomCode();
    }

    public void deleteUser(){
        this.isValid = false;
    }

    public void changeName(String firstName, String lastName){
        assertName(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changePushToken(String pushToken) {
        assertPushToken(pushToken);
        this.pushToken = pushToken;
    }

    /**
     * changeProfileBadge
     * @param badge profile badge
     */
    public void changeProfileBadge(Badge badge){
        this.profileBadge = badge;
    }

    private void assertName(String firstName, String lastName){
        if(firstName.isEmpty() || lastName.isEmpty()){
            throw new GlobalException("이름 정보가 잘못 됐습니다.", GlobalErrorCode.USER_VALID_EXCEPTION);
        }
    }

    private void assertPushToken(String pushToken){
        if(pushToken == null)
            throw new GlobalException("push token이 유효하지 않습니다.", GlobalErrorCode.VALID_EXCEPTION);
    }

    public String getFullName(){
        return getLastName() + getFirstName();
    }
}

