package com.example.demo.service;

import com.example.demo.domain.User.Login;
import com.example.demo.domain.User.User;
import com.example.demo.dto.Login.Response.ResponseLoginDto;
import com.example.demo.repository.LoginRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired LoginService loginService;
    @Autowired
    LoginRepository loginRepository;
    @Autowired UserService userService;

    /**
     * 회원 저장 및 로그인
     */
    @Test
    public void 로그인_성공() throws Exception{

        //given

        User findUser = userService.findOne(1L);
        Login login = new Login(findUser,"zeun@email",passwordEncoder.encode("1234"),null);

        //when
        loginRepository.save(login);

        String pw = "1234";

        // 비밀번호 매칭
        ResponseLoginDto responseLoginDto = loginService.matchPw("zeun@email", pw);


        //then
        Assertions.assertThat(responseLoginDto.isMatchResult()).isTrue();
        Assertions.assertThat(responseLoginDto.getUser().get().getUserCode()).isEqualTo(1l);
    }

    @Test
    public void 로그인_실패_있는아이디() throws Exception{

        //given
        String pw = "123456";

        // when 비밀번호 매칭
        ResponseLoginDto responseLoginDto = loginService.matchPw("zzz@email.com", pw);

        System.out.println(responseLoginDto);

        //then
        Assertions.assertThat(responseLoginDto.isMatchResult()).isFalse();
        Assertions.assertThat(responseLoginDto.getUser()).isEmpty();
    }

    @Test
    public void 로그인_실패_없는아이디() throws Exception{

        //given

        String pw = "1234";

        // when 비밀번호 매칭
        ResponseLoginDto responseLoginDto = loginService.matchPw("null@email", pw);

        System.out.println(responseLoginDto);

        //then
        Assertions.assertThat(responseLoginDto.isMatchResult()).isFalse();
        Assertions.assertThat(responseLoginDto.getUser()).isEmpty();
    }


}