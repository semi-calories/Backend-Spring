package com.example.demo.dto.Recommend.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
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
