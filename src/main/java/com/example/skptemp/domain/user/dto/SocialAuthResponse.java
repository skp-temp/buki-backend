package com.example.skptemp.domain.user.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class SocialAuthResponse {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("expires_in")
    private Long expiresIn;
    @SerializedName("scope")
    private String scope;
    @SerializedName("refresh_token_expires_in")
    private Long refreshTokenExpiresIn;
}
