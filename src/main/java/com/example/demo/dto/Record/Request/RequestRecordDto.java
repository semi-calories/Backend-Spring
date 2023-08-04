package com.example.demo.dto.Record.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiOperation(
        value = "식단 기록 저장 요청 by 텍스트",
        notes = "사용자가 텍스트로 입력한 식단 기록 저장을 요청한다.")
public class RequestRecordDto {
    @JsonProperty("user_code")
    private Long userCode;
    @JsonProperty("eat_date")
    private LocalDateTime eatDate;
    @JsonProperty("food_weight")
    private Long foodWeight;
    @JsonProperty("food_times")
    private int foodTimes;
    @JsonProperty("food_code")
    private Long foodCode;
    @JsonProperty("food_name")
    private String foodName;
    @JsonProperty("food_kcal")
    private Double foodKcal;
    @JsonProperty("food_carbo")
    private Double foodCarbo;
    @JsonProperty("food_protein")
    private Double foodProtein;
    @JsonProperty("food_fat")
    private Double foodFat;
    @JsonProperty("satisfaction")
    private int satisfaction;

}
