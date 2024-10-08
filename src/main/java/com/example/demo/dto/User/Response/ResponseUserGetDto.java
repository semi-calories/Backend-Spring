package com.example.demo.dto.User.Response;

import com.example.demo.domain.User.Gender;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
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
    private Integer age;
    private Gender gender;
    private String image;
    private Double height;
    private Double weight;

    private String userActivity;
    private Double goalWeight;
    private String userGoal;
    private Double kcal;
    private Double carbo;
    private Double protein;
    private Double fat;

    private int period;

    public ResponseUserGetDto(User user, UserGoal userGoal) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
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
        this.period = userGoal.getGoalPeriod();
    }
}
