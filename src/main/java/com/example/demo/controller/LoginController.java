package com.example.demo.controller;


import com.example.demo.config.JWT.JwtProvider;
import com.example.demo.dto.Login.Request.RequestPwMatchDto;
import com.example.demo.dto.Login.Request.RequestPwUpdateDto;
import com.example.demo.dto.Login.Request.RequestSignUpDto;
import com.example.demo.dto.Login.Response.ResponseEmailCheckDto;
import com.example.demo.dto.Login.Response.ResponseLoginDto;
import com.example.demo.dto.Login.Response.ResponseSaveDto;
import com.example.demo.dto.User.Request.RequestDeleteUserDto;
import com.example.demo.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final JwtProvider jwtProvider;

    /**
     * 회원 정보 저장 (= 회원 가입)
     */
    @PostMapping("/sign-up")
    public ResponseSaveDto signUp(@RequestBody @Valid RequestSignUpDto requestSignUpDto){
        return loginService.save(requestSignUpDto);
    }

    /**
     * 아이디 중복 확인
     */
    @GetMapping("/emailDuplicateCheck")
    public ResponseEmailCheckDto emailDuplicateCheck(String email){

        // 중복 조회 후 값 리턴 true: 중복 o, false: 중복 x
        return new ResponseEmailCheckDto(loginService.emailDuplicateCheck(email));
    }

    /**
     * 회원 탈퇴(정보 삭제)
     */
    @PostMapping("/deleteInfo")
    public void deleteInfo(@RequestBody RequestDeleteUserDto requestDeleteUserDto){
        loginService.deleteLogin(requestDeleteUserDto.getUserCode());
    }


    /**
     * 비밀번호 변경
     */
    @PostMapping("/passwordUpdate")
    public ReturnDto passwordUpdate(@RequestBody RequestPwUpdateDto requestPwUpdateDto) throws Exception {
        try {
            loginService.updatePw(requestPwUpdateDto.getUserCode(), requestPwUpdateDto.getPassword());
            return new ReturnDto<>(true);
        }catch (Exception e){
            return new ReturnDto<>(false);
        }
    }

    /**
     * 비밀번호 match = 로그인
     */
    @PostMapping("/passwordMatch")
    public ResponseLoginDto passwordMatch(@RequestBody @Valid RequestPwMatchDto requestPwMatchDto){
        ResponseLoginDto responseLoginDto = loginService.matchPw(requestPwMatchDto.getUserEmail(), requestPwMatchDto.getUserPassword());
        return responseLoginDto;
    }

    /**
     * 로그아웃
     */
    @GetMapping("/userLogout")
    public ReturnDto logout(HttpServletRequest request, Long userCode){
        String encryptedRefreshToken = jwtProvider.resolveRefreshToken(request);
        String accessToken = jwtProvider.resolveAccessToken(request);
        loginService.logout(encryptedRefreshToken, accessToken, userCode);
        return new ReturnDto("ok");
    }



    @AllArgsConstructor
    @Getter
    static class ReturnDto<T>{
        private T response;
    }

}
