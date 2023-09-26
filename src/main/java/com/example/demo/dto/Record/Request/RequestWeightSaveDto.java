package com.example.demo.dto.Record.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 몸무게 저장/수정",
        notes = "유저의 몸무게 통계를 위해 몸무게를 저장/수정한다.")
public class RequestWeightSaveDto {
    private Long userCode;
    private String timestamp;
    private Double userWeight;
}
