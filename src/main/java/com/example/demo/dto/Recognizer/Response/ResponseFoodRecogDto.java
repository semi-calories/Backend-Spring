package com.example.demo.dto.Recognizer.Response;

import com.example.demo.domain.DB.DietList;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@ApiOperation(
        value = "음식 사진 인식 응답",
        notes = "음식 사진의 인식을 응답한다.")
public class ResponseFoodRecogDto {

    private List<DietListDto> dietLists = new ArrayList<>();

    public ResponseFoodRecogDto(List<DietList> dietList) {
        for (DietList list : dietList) {
            DietListDto dietListDto = new DietListDto(list);
            this.dietLists.add(dietListDto);
        }
    }

    @Getter
    @NoArgsConstructor
    protected class DietListDto {

        private Long foodCode;
        private String foodName;
        private String foodMainCategory;
        private String foodDetailClassification;
        private double foodWeight;
        private double foodKcal;
        private double foodCarbo;
        private double foodProtein;
        private double foodFat;

        public DietListDto(DietList dietList) {
            this.foodCode = dietList.getFoodCode();
            this.foodName = dietList.getFoodName();
            this.foodMainCategory = dietList.getFoodMainCategory();
            this.foodDetailClassification = dietList.getFoodDetailedClassification();
            this.foodWeight = dietList.getFoodWeight();
            this.foodKcal = dietList.getFoodKcal();
            this.foodCarbo = dietList.getFoodCarbo();
            this.foodProtein = dietList.getFoodProtein();
            this.foodFat = dietList.getFoodFat();
        }
    }
}
