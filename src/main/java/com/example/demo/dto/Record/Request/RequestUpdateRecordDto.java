package com.example.demo.dto.Record.Request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiOperation(
        value = "식단 기록 수정 요청",
        notes = "사용자가 입력한 식단 기록 수정을 요청한다.")
public class RequestUpdateRecordDto {

    private Long userCode;

    private String originalEatDate;

    private Long originalFoodCode;

    // 여기서 부터 수정
    private String eatDate;

    private Double foodWeight;

    private Long foodCode;

    private String foodName;

    private Double foodKcal;

    private Double foodCarbo;

    private Double foodProtein;

    private Double foodFat;

    private int satisfaction;

}
