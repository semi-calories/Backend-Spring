package com.example.demo.dto.User.Request;

import com.example.demo.domain.User.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 정보 수정",
        notes = "유저의 정보를 수정한다.")
public class RequestUserUpdateDto {

    @JsonProperty("user-code")
    private Long userCode;
    private String email;
    private String image;
    private String name;
    private Gender gender;
    private int age;
    private double height;
    private double weight;

    @JsonProperty("activity")
    private String userActivity;
    @JsonProperty("goal-weight")
    private double goalWeight;
    @JsonProperty("goal")
    private String userGoal;
}
