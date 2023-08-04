package com.example.demo.dto.Recommend.FastAPI;

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
        value = "음식 추천 FastAPI 응답",
        notes = "FastAPI에서 사용자가 먹을 음식 추천을 응답한다.")
public class ResponseRecommendAPIDto {

    private List<Integer> foodCodeList;
    private List<String> foodNameList;
    private List<String> foodMainCategoryList;
    private List<String> foodDetailedClassificationList;
    private List<Double> foodWeightList;
    private List<Double> foodKcalList;
    private List<Double> foodCarbonList;
    private List<Double> foodProteinList;
    private List<Double> foodFatList;
}
