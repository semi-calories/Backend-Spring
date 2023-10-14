package com.example.demo.dto.Login.Request;

import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
}
