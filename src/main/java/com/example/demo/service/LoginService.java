package com.example.demo.service;

import com.example.demo.config.JWT.JwtProvider;
import com.example.demo.domain.User.CustomUserDetails;
import com.example.demo.domain.User.Login;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.Login.Request.RequestSignUpDto;
import com.example.demo.dto.Login.Response.ResponseLoginDto;
import com.example.demo.dto.Login.Response.ResponseSaveDto;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.UserGoalRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final RedisTemplate<String, String> redisTemplate;
    /**
     * 저장
     */
    public ResponseSaveDto save(RequestSignUpDto requestSignUpDto){


        // 유저(+목표) 객체 생성 및 DB에 저장
        User saveUser = userRepository.save(new User(requestSignUpDto.getEmail(), requestSignUpDto.getName()));
        userGoalRepository.save(new UserGoal(saveUser));

        // 로그인 객체 생성
        Login login = new Login(saveUser,requestSignUpDto.getEmail(), requestSignUpDto.getPassword(), null);
        // 비밀번호 암호화
        login.passwordEncode( requestSignUpDto.getPassword(), passwordEncoder);
        // DB에 저장 = 회원 가입
        loginRepository.save(login);

        // token 얻기
        List<String> tokenList = getToken(login);

        return new ResponseSaveDto(login.getUserCode().getUserCode(), tokenList.get(0), tokenList.get(1));

    }

    /**
     * 삭제
     */
    public void deleteLogin(Long userCode) {

        User findUser = userRepository.findById(userCode)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        userRepository.delete(findUser);

    }

    /**
     * 유저 이메일 수정
     */
    public void updateEmail(Long userCode, String email){
        Optional<Login> findUserLogin = loginRepository.findByUserCode(userCode);

        findUserLogin.ifPresent(
                userLogin -> userLogin.changeEmail(email)
        );
    }


    /**
     * 유저 아이디 중복 체크
     */
    public boolean emailDuplicateCheck(String email){
        Long emailCount = loginRepository.countByUserEmail(email);
        if(emailCount>0){
            return true; // 중복 o
        }
        else return false; // 중복 x
    }

    /**
     * 비밀번호 변경
     */
    public String updatePw(Long userCode,String password){

        Optional<Login> login = loginRepository.findByUserCode(userCode);
        if (login.isPresent()){
            String newPassword = login.get().changePassword(login.get().passwordEncode(password, passwordEncoder));
            return newPassword;
        }
        else throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
    }

    /**
     * 비밀번호 match = 로그인
     */
    public ResponseLoginDto matchPw(String userEmail, String userPw){
        Optional<Login> login = loginRepository.findByUserEmail(userEmail);

        // 유저 존재
        if (login.isPresent()){
            boolean matches = passwordEncoder.matches(userPw, login.get().getUserPassword());
            if(matches==true){
                // 비밀번호 매칭 성공
                User user = login.get().getUserCode();
                List<String> tokenList = getToken(login.get());

                return new ResponseLoginDto(true, Optional.of(user),true, tokenList.get(0),tokenList.get(1));
            // 매칭 실패
            }else return new ResponseLoginDto(true, Optional.empty(),false, null,null);
        }else{
            // 유저 존재 x
            return new ResponseLoginDto(false, Optional.empty(),false, null,null);

        }
    }

    /**
     * 토큰 생성 및 db에 refresh token db 저장
     */
    private List<String> getToken(Login login) {

        // 토큰 담기위한 list
        List<String> tokenList = new ArrayList<>();

        // 토큰 생성
        tokenList.add(jwtProvider.generateAccessToken(new CustomUserDetails(login.getUserEmail(), login.getUserPassword())).getAccessToken());
        String newRefreshToken = jwtProvider.generateRefreshToken(new CustomUserDetails(login.getUserEmail(), login.getUserPassword())).getRefreshToken();
        tokenList.add(newRefreshToken);

        // redis에 refresh token 저장, db에 저장
        System.out.println("############## newRefreshToken 로그인할때 저장!! = " + newRefreshToken);
        redisTemplate.opsForValue().set(login.getUserEmail(), newRefreshToken);
        login.changeToken(newRefreshToken);
//        loginRepository.bulkModifyingByUserCode(login.getUserCode().getUserCode(),newRefreshToken);
        return tokenList;
    }


    @Transactional
    public void logout(String encryptedRefreshToken, String accessToken) {
        // token에서 로그인한 사용자 정보 get해 로그아웃 처리

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(redisTemplate.opsForValue().get(email)!=null){
            // redis에서 삭제
            redisTemplate.delete(email);
            // 블랙리스트 처리
            Long expiration = jwtProvider.getExpiration(accessToken);
            redisTemplate.opsForValue().set(accessToken, "logout",expiration, TimeUnit.MICROSECONDS);

        }
    }
}
