package com.example.demo.dto.User.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 정보 삭제",
        notes = "유저의 정보를 삭제한다.")
public class RequestDeleteUserDto {

    private Long userCode;
}
