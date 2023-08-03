package com.example.demo.dto.User.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 목표 정보 수정",
        notes = "유저의 목표 정보를 수정한다.")
public class RequestUserGoalUpdateDto {

    @JsonProperty("user-code")
    private Long userCode;
    @JsonProperty("activity")
    private String userActivity;
    @JsonProperty("goal-weight")
    private double goalWeight;
    @JsonProperty("goal")
    private String userGoal;
    private Long kcal;
    private Long carbo;
    private Long protein;
    private Long fat;

}
