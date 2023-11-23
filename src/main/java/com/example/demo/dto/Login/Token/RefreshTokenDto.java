package com.example.demo.dto.Login.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RefreshTokenDto {

    private String grantType;
    private String authType;
    private String refreshToken;
    private Long refreshTokenExpiresIn;

}
