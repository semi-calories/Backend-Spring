package com.example.demo.dto.Recommend.Response;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "음식 추천 응답",
        notes = "FASTAPI에서 사용자가 먹을 음식 목록을 추천한다.")
public class RecommendDto {
    private Integer foodCode;
    private String foodName;
    private String foodMainCategory;
    private String foodDetailedClassification;
    private Double foodWeight;
    private Double foodKcal;
    private Double foodCarbon;
    private Double foodProtein;
    private Double foodFat;
}
