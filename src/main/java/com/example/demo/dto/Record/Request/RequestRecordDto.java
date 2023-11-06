package com.example.demo.dto.Record.Request;

import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiOperation(
        value = "식단 기록 저장 요청 by 텍스트",
        notes = "사용자가 텍스트로 입력한 식단 기록 저장을 요청한다.")
public class RequestRecordDto {

    @NotNull
    private Long userCode;

    @NotBlank
    private String eatDate;

    @NotNull
    private Double foodWeight;

    @NotNull
    private Long foodCode;

    @NotBlank
    private String foodName;

    @NotNull
    private Double foodKcal;

    @NotNull
    private Double foodCarbo;

    @NotNull
    private Double foodProtein;

    @NotNull
    private Double foodFat;

    private int satisfaction;

}
