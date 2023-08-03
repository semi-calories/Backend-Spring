package com.example.demo.dto.Recognizer.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiOperation(
        value = "음식 사진 인식 응답",
        notes = "음식 사진의 인식을 응답한다.")
public class ResponseFoodRecogDto {

    @JsonProperty("food-name-list")
    private List<String> foodNameList = new ArrayList<>();

    @JsonProperty("food-code-list")
    private List<Integer> foodCodeList = new ArrayList<>();

}
