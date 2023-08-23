package com.example.demo.dto.Login.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 비밀번호 매칭",
        notes = "유저의 비밀번호가 db값과 일치하는지 확인한다.")
public class RequestPwMatchDto {

    private String email;

    private String password;
}
