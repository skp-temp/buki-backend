package com.example.skptemp.domain.user.entity;

import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Entity
public class User {
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String token;
    private Long point;
    @Column(name = "kakao_id")
    private Long kakaoId;
    private String authority;

    protected User(){
    }
    private User(String uuid, Long kakaoId, String authority){
        this.code = uuid;
        this.point = 0L;
        this.kakaoId = kakaoId;
        this.authority = authority;
    }

    public static User createUser(Long kakaoId){
        String uuid = makeUuid(false);
        return new User(uuid, kakaoId, "USER");
    }

    public void changeName(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private static String makeUuid(boolean isHyphen){
        if(isHyphen)
            return UUID.randomUUID().toString();
        else
            return UUID.randomUUID().toString().replace("-", "");
    }

    private void assertName(String firstName, String lastName){
        if(firstName.isEmpty() || lastName.isEmpty()){
            throw new GlobalException("이름 정보가 잘못되었습니다.", GlobalErrorCode.VALID_EXCEPTION);
        }
    }
}
