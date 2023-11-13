package com.example.demo.dto.Login.Response;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 회원가입 응답",
        notes = "유저의 회원가입 요청에 응답한다.")
@ToString
public class ResponseSaveDto {
    private Long userCode;
    private String accessToken;
    private String refreshToken;
}
