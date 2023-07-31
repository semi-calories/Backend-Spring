package com.example.demo.dto.Record.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
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
