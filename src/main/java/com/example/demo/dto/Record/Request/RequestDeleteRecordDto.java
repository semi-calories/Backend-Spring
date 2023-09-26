package com.example.demo.dto.Record.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiOperation(
        value = "식단 기록 저장 요청 by 텍스트",
        notes = "사용자가 텍스트로 입력한 식단 기록 저장을 요청한다.")
public class RequestDeleteRecordDto {
    private Long userCode;
    private Long foodCode;
    private String eatDate;
}
