package com.example.demo.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class TokenDto {

    private String grantType;
    private String authType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

}
