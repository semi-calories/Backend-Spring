package com.example.demo.dto.Record.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 몸무게 삭제",
        notes = "유저의 몸무게 통계를 위해 몸무게를 삭제한다.")
public class RequestWeightDeleteDto {
    private Long userCode;
    private String timestamp;
}
