package com.example.demo.dto.Recommend.FastAPI;

import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestRecommendAPIDto {

    // 목표, 유저정보(성별, 키, 체중, 나이, 활동계수), 선호음식 리스트, 비선호음식 리스트, 첫끼인지(T/F),
    // 일일 영양소 섭취량 기록(나머지 끼니일 때만 입력)

    private String goal;
    private String gender;
    private double height;
    private double weight;
    private int age;
    private String activity;
    private List<String> prefer;
    private List<String> dislike;
    private Boolean firstFood;
    private List<String> nutrient;

    public RequestRecommendAPIDto(UserGoal user, Boolean firstFood) {
        this.goal = user.getUserGoal();
        this.gender = user.getUserCode().getGender().toString();
        this.height = user.getUserCode().getHeight();
        this.weight = user.getUserCode().getWeight();
        this.age = user.getUserCode().getAge();
        this.activity = user.getUserActivity();
//        this.prefer = user.getPreferFoodCode();
//        this.dislike = user.getDislikeFoodCode();

        this.prefer = null;
        this.dislike = null;
        this.firstFood = firstFood;
        this.nutrient = null;
    }
}
