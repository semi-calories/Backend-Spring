package com.example.demo.dto.Recognizer.Response;

import com.example.demo.domain.DB.DietList;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
@ApiOperation(
        value = "음식 사진 인식 응답",
        notes = "음식 사진의 인식을 응답한다.")
public class ResponseFoodRecogDto {

    private List<DietListDto> dietList;

    public ResponseFoodRecogDto(List<DietList> dietList) {
        for (DietList list : dietList) {
            DietListDto dietListDto = new DietListDto(list);
            this.dietList.add(dietListDto);
        }
    }

    private class DietListDto {
        @JsonProperty("food-code")
        private Long foodCode;
        private String name;
        @JsonProperty("main-category")
        private String mainCategory;
        @JsonProperty("detail-category")
        private String detailCategory;
        private Long weight;
        private double kcal;
        private double carbo;
        private double protein;
        private double fat;

        public DietListDto(DietList dietList) {
            this.foodCode = dietList.getFoodCode();
            this.name = dietList.getFoodName();
            this.mainCategory = dietList.getFoodMainCategory();
            this.detailCategory = dietList.getFoodDetailedClassification();
            this.weight = dietList.getFoodWeight();
            this.kcal = dietList.getFoodKcal();
            this.carbo = dietList.getFoodCarbo();
            this.protein = dietList.getFoodProtein();
            this.fat = dietList.getFoodFat();
        }
    }
}
