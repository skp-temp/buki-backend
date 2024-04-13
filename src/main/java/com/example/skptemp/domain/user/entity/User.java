package com.example.skptemp.domain.user.entity;

import com.example.skptemp.domain.common.BaseEntity;
import com.example.skptemp.global.constant.LoginType;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; // 친구 추가 용도 발급 코드를 의미
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String pushToken;       // firebase 토큰
    @Enumerated(EnumType.STRING)
    private LoginType loginType;    // authentication 플랫폼 타입
    private String authProviderId;  // authentication 플랫폼 제공 identifier
    private String authority;
    private boolean isValid;        // 논리적 삭제 처리를 위함

    protected User(){
    }
    private User(String uuid, LoginType loginType, String authProviderId, String authority){
        this.code = uuid;
        this.loginType = loginType;
        this.authProviderId = authProviderId;
        this.authority = authority;
        this.isValid = true;
    }

    private User(String uuid, LoginType loginType, String authProviderId, String authority, String pushToken){
        this.code = uuid;
        this.loginType = loginType;
        this.authProviderId = authProviderId;
        this.authority = authority;
        this.pushToken = pushToken;
        this.isValid = true;
    }

    public static User createUser(LoginType loginType, String authProviderId, String firstName, String lastName, String pushToken){
        String uuid = makeUuid(false);
        return new User(uuid, loginType, authProviderId, "USER", pushToken);
    }

    public void deleteUser(){
        this.isValid = false;
    }

    public void changeName(String firstName, String lastName){
        assertName(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private static String makeUuid(boolean hasHypen){
        if(hasHypen)
            return UUID.randomUUID().toString();
        else
            return UUID.randomUUID().toString().replace("-", "");
    }

    private void assertName(String firstName, String lastName){
        if(firstName.isEmpty() || lastName.isEmpty()){
            throw new GlobalException("이름 정보가 잘못 됐습니다.", GlobalErrorCode.USER_VALID_EXCEPTION);
        }
    }
}
