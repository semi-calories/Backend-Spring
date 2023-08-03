package com.example.demo.dto.User.Response;

import com.example.demo.domain.Gender;
import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 목표 정보 조회 응답",
        notes = "유저의 목표 정보 조회 요청에 응답한다.")
public class ResponseUserGoalGetDto {

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

    public ResponseUserGoalGetDto(UserGoal userGoal) {
        this.userActivity = userGoal.getUserActivity();
        this.goalWeight = userGoal.getGoalWeight();
        this.userGoal = userGoal.getUserGoal();
        this.kcal = userGoal.getKcal();
        this.carbo = userGoal.getCarbo();
        this.protein = userGoal.getProtein();
        this.fat = userGoal.getFat();
    }
}
