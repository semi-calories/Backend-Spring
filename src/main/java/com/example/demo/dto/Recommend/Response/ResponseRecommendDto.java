package com.example.demo.dto.Recommend.Response;

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
        notes = "이미지를 포함하여 사용자가 먹을 음식을 추천한다.")
public class ResponseRecommendDto {
    // Image 있음
    private Integer foodCode;
    private String foodName;
    private String foodMainCategory;
    private String foodImgUrl;
    private String foodDetailedClassification;
    private Double foodWeight;
    private Double foodKcal;
    private Double foodCarbon;
    private Double foodProtein;
    private Double foodFat;
}
