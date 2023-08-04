package com.example.demo.dto.User.Response;

import com.example.demo.domain.Gender;
import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "유저 정보 조회 응답",
        notes = "유저의 정보 조회 요청에 응답한다.")
public class ResponseUserGetDto {

    private String email;
    private String name;
    private int age;
    private Gender gender;
    private String phone;
    private byte[] image;
    private double height;
    private double weight;

    @JsonProperty("activity")
    private String userActivity;
    @JsonProperty("goal-weight")
    private double goalWeight;
    @JsonProperty("goal")
    private String userGoal;
    private Double kcal;
    private Double carbo;
    private Double protein;
    private Double fat;

    public ResponseUserGetDto(User user, UserGoal userGoal) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.image = user.getImage();
        this.height = user.getHeight();
        this.weight = user.getWeight();

        this.userActivity = userGoal.getUserActivity();
        this.userGoal = userGoal.getUserGoal();
        this.goalWeight = userGoal.getGoalWeight();
        this.kcal = userGoal.getKcal();
        this.carbo = userGoal.getCarbo();
        this.protein = userGoal.getProtein();
        this.fat = userGoal.getFat();
    }
}
