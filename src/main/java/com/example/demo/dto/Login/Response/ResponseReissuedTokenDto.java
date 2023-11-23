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
        value = "유저 새로운 access token 발급 응답",
        notes = "유저의 새로운 access token 발급 요청에 응답한다.")
@ToString
public class ResponseReissuedTokenDto {

    private String accessToken;
}
