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
        value = "유저 비밀번호 수정",
        notes = "유저의 비밀번호를 수정한다.")
public class RequestPwUpdateDto {

    @JsonProperty(value = "user-code")
    private Long userCode;

    private String password;
}
