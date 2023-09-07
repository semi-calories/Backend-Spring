package com.example.demo.dto.Record.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiOperation(
        value = "식단 기록 저장 요청 by 텍스트",
        notes = "사용자가 텍스트로 입력한 식단 기록 저장을 요청한다.")
public class RequestRecordDto {

    private Long userCode;

    private String eatDate;

    private Long foodWeight;

    private Long foodCode;

    private String foodName;

    private Double foodKcal;

    private Double foodCarbo;

    private Double foodProtein;

    private Double foodFat;

    private int satisfaction;

}
