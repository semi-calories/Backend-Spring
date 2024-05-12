package com.example.demo.controller;

import com.example.demo.config.JWT.JwtProvider;
import com.example.demo.dto.Login.Response.ResponseReissuedTokenDto;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController{
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/reissueToken")
    public ResponseReissuedTokenDto reissueToken(HttpServletRequest request, Long userCode){

        String encryptedRefreshToken = jwtProvider.resolveRefreshToken(request);

        // 새로운 access token 발급해 응답
        return new ResponseReissuedTokenDto(authService.reissueAccessToken(encryptedRefreshToken, userCode));
    }

}
