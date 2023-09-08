package com.example.demo.dto.Recommend.Request;

//
import com.example.demo.domain.User.Diet.DietRecord;
import com.example.demo.domain.User.Diet.UserDietDislike;
import com.example.demo.domain.User.Diet.UserDietPrefer;
import com.example.demo.domain.User.UserGoal;

import java.util.ArrayList;
import java.util.stream.Collectors;
//
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiOperation(
        value = "음식 추천 응답",
        notes = "사용자가 먹을 음식 추천을 응답한다.")
public class ResponseRecommendDto {
    private List<Integer> foodCodeList;
    private List<String> foodNameList;
    private List<String> foodMainCategoryList;
    private List<String> foodImgUrl;
    private List<String> foodDetailedClassificationList;
    private List<Double> foodWeightList;
    private List<Double> foodKcalList;
    private List<Double> foodCarbonList;
    private List<Double> foodProteinList;
    private List<Double> foodFatList;


    /*
    *     public RequestRecommendAPIDto(UserGoal user, int eatTimes, List<UserDietPrefer> preferDiet, List<UserDietDislike> dislikeDiet, List<DietRecord> dietRecords) {
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
    *
    *
    * */
}
