package com.example.demo.service;

import com.example.demo.config.JWT.JwtProvider;
import com.example.demo.domain.User.CustomUserDetails;
import com.example.demo.domain.User.Login;
import com.example.demo.dto.Login.Token.AccessTokenDto;
import com.example.demo.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtProvider jwtProvider;
    private final LoginRepository loginRepository;
    private final RedisTemplate<String, String > redisTemplate;


    public String reissueAccessToken(String encryptedRefreshToken, Long userCode){
        verifiedRefreshToken(encryptedRefreshToken);

        // refresh token이 db에 저장된 값과 일치하는지 확인, 일치시 access token 발급
        String newAccessToken = refreshTokenMatches(encryptedRefreshToken, userCode);

        return newAccessToken;

    }


    @Transactional
    String refreshTokenMatches(String refreshToken, Long userCode) {
        Optional<Login> findLogin = loginRepository.findByUserCode(userCode);

        if(findLogin.isPresent()){
            String redisRefreshToken = redisTemplate.opsForValue().get(findLogin.get().getUserEmail());


            // 클라이언트가 보낸 refresh가 db의 refresh 및 redis의 refresh와 동일한지 확인
            if(findLogin.get().getUserToken().equals(refreshToken) && redisRefreshToken.equals(refreshToken)){
                // access 토큰 생성
                AccessTokenDto newAccessToken = jwtProvider.generateAccessToken(new CustomUserDetails(findLogin.get().getUserEmail(), findLogin.get().getUserPassword()));
                return newAccessToken.getAccessToken();
            }else{
                throw new IllegalArgumentException("사용불가능한 refresh token입니다.");
            }
        }else throw new IllegalArgumentException("존재하지 않는 사용자입니다.");

    }

    private void verifiedRefreshToken(String encryptedRefreshToken){
        if(encryptedRefreshToken == null){
            throw new IllegalArgumentException("refresh token 사용 불가");
        }
    }



}
