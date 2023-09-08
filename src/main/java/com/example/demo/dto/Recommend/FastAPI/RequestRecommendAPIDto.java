package com.example.demo.dto.Recommend.FastAPI;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.User.UserGoal;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiOperation(
        value = "음식 추천 FastAPI 요청",
        notes = "FastAPI에 사용자가 먹을 음식 추천을 요청한다.")
public class RequestRecommendAPIDto {

    // 목표, 유저정보(성별, 키, 체중, 나이, 활동계수), 선호음식 리스트, 비선호음식 리스트, 현재 추천 끼니 시기,
    // 일일 영양소 섭취량 기록

    private String goal;
    private String gender;
    private double height;
    private double weight;
    private int age;
    private String activity;
    private List<Long> prefer;
    private List<Long> dislike;

    private int eatTimes;
    private List<Double> nutrient;

    public RequestRecommendAPIDto(UserGoal user, int eatTimes, List<UserDietPrefer> preferDiet, List<UserDietDislike> dislikeDiet, List<DietRecord> dietRecords) {
        // 선호 음식 리스트 생성
        List<Long> preferCollect = getPreferCollect(preferDiet);
        // 비선호 음식 리스트 생성
        List<Long> dislikeCollect = getDislikeCollect(dislikeDiet);
        // 식단 기록 리스트 생성
        List<Double> dietRecordsList = getDietRecords(dietRecords);

        this.goal = user.getUserGoal();
        this.gender = user.getUserCode().getGender().toString();
        this.height = user.getUserCode().getHeight();
        this.weight = user.getUserCode().getWeight();
        this.age = user.getUserCode().getAge();
        this.activity = user.getUserActivity();
        this.prefer = preferCollect;
        this.dislike = dislikeCollect;
        this.eatTimes = eatTimes;
        this.nutrient = dietRecordsList;
    }

    private static List<Long> getPreferCollect(List<UserDietPrefer> preferDiet) {
        List<Long> preferCollect = preferDiet.stream().map(p -> p.getPreferFoodCode().getFoodCode()).collect(Collectors.toList());
        return preferCollect;
    }

    private static List<Long> getDislikeCollect(List<UserDietDislike> preferDiet) {
        List<Long> dislikeCollect = preferDiet.stream().map(p -> p.getDislikeFoodCode().getFoodCode()).collect(Collectors.toList());
        return dislikeCollect;
    }

    private static List<Double> getDietRecords(List<DietRecord> dietRecords){
        // 0 총칼로리, 123 탄단지
        List<Double> arr = new ArrayList<>();
        if(dietRecords.size()>1){
            Double totalKcal = dietRecords.stream().map(dr -> dr.getFoodKcal()).mapToDouble(i->i).sum();
            Double totalCarbo = dietRecords.stream().map(dr -> dr.getFoodCarbo()).mapToDouble(i->i).sum();
            Double totalProtein = dietRecords.stream().map(dr -> dr.getFoodProtein()).mapToDouble(i->i).sum();
            Double totalFat = dietRecords.stream().map(dr -> dr.getFoodFat()).mapToDouble(i->i).sum();
            arr.add(totalKcal);
            arr.add(totalCarbo);
            arr.add(totalProtein);
            arr.add(totalFat);
        }else{
            arr = Arrays.asList(0.0,0.0,0.0,0.0);
        }

        return arr;
    }

}
