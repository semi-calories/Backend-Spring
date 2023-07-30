package com.example.demo.dto.Recommend.FastAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRecommendAPIDto {
    //private List<Integer> result;
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
