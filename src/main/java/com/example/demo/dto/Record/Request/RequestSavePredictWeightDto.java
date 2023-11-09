package com.example.demo.dto.Record.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiOperation(
        value = "예상 몸무게 저장 요청 ",
        notes = "사용자의 목표 몸무게와 예상 기간으로 앞으로의 예상 몸무게를 저장한다.")
public class RequestSavePredictWeightDto {

    private Long userCode;
    private Double goalWeight;
    private int period;
}
