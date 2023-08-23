package com.example.demo.dto.Login.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 회원 가입",
        notes = "유저의 정보를 DB에 처음 저장한다.")
public class RequestSignUpDto {

    private String email;
    private String password;
    private String name;
}
