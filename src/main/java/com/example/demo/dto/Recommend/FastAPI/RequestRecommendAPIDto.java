package com.example.demo.dto.Recommend.FastAPI;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Long> prefer;
    private List<Long> dislike;

    private int eatTimes;
    private List<Double> nutrient;

    public RequestRecommendAPIDto(UserGoal user, int eatTimes, List<UserDietPrefer> preferDiet, List<UserDietDislike> dislikeDiet, List<DietRecord> dietRecords) {
        List<Long> preferCollect = getPreferCollect(preferDiet);
        List<Long> dislikeCollect = getDislikeCollect(dislikeDiet);
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
        Double totalKcal = dietRecords.stream().map(dr -> dr.getFoodKcal()).mapToDouble(i->i).sum();
        Double totalCarbo = dietRecords.stream().map(dr -> dr.getFoodCarbo()).mapToDouble(i->i).sum();
        Double totalProtein = dietRecords.stream().map(dr -> dr.getFoodProtein()).mapToDouble(i->i).sum();
        Double totalFat = dietRecords.stream().map(dr -> dr.getFoodFat()).mapToDouble(i->i).sum();
        arr.add(totalKcal);
        arr.add(totalCarbo);
        arr.add(totalProtein);
        arr.add(totalFat);
        return arr;
    }

}
