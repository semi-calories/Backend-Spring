package com.example.demo.dto.User.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 몸무게 저장",
        notes = "유저의 몸무게 통계를 위해 몸무게를 저장한다.")
public class RequestSaveWeightDto {
    private Long userCode;
    private Double userWeight;
    // 날찌..?
}
