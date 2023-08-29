package com.example.demo.service;

import com.example.demo.domain.User.Login;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.Login.Request.RequestSignUpDto;
import com.example.demo.dto.Login.Response.ResponseLoginDto;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.UserGoalRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 저장
     */
    public Long save(RequestSignUpDto requestSignUpDto){

        // 유저(+목표) 객체 생성 및 DB에 저장
        User saveUser = userRepository.save(new User(requestSignUpDto.getEmail(), requestSignUpDto.getName()));
        userGoalRepository.save(new UserGoal(saveUser));

        // 로그인 객체 생성
        Login login = new Login(saveUser,requestSignUpDto.getEmail(), requestSignUpDto.getPassword(), null);
        // 비밀번호 암호화
        login.passwordEncode( requestSignUpDto.getPassword(), passwordEncoder);
        // DB에 저장 = 회원 가입
        loginRepository.save(login);
        return saveUser.getUserCode();
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
     * 비밀번호 match
     */
    public ResponseLoginDto matchPw(String userEmail, String userPw){
        Optional<Login> login = loginRepository.findByUserEmail(userEmail);

        // 유저 존재
        if (login.isPresent()){
            boolean matches = passwordEncoder.matches(userPw, login.get().getUserPassword());
            if(matches==true){
                // 비밀번호 매칭 성공
                User user = login.get().getUserCode();
                return new ResponseLoginDto(true, Optional.of(user),true);
            // 매칭 실패
            }else return new ResponseLoginDto(true, Optional.empty(),false);
        }else{
            // 유저 존재 x
            return new ResponseLoginDto(false, Optional.empty(),false);

        }
    }
}
