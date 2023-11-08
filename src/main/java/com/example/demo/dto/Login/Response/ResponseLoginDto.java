package com.example.demo.dto.Login.Response;


import com.example.demo.domain.User.User;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 로그인 가능 여부 응답",
        notes = "유저의 로그인 요청에 응답한다.")
@ToString
public class ResponseLoginDto {

    private boolean userExists;
    private Optional<User> user;
    private boolean matchResult;
    private String accessToken;
    private String refreshToken;

}
