package com.example.demo.dto.Login.Response;


import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 이메일 중복 조회 응답",
        notes = "유저의 이메일 중복 조회에 응답한다.")
public class ResponseEmailCheckDto {
    private boolean duplicateResult;
}
