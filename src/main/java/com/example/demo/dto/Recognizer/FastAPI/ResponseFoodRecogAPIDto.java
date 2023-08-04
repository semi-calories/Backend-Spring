package com.example.demo.dto.Recognizer.FastAPI;

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
        value = "음식 사진 인식 FastAPI 응답",
        notes = "FastAPI에서 음식 사진의 인식을 응답한다.")
public class ResponseFoodRecogAPIDto {

    private List<String> foodNameList = new ArrayList<>();

    private List<Long> foodCodeList = new ArrayList<>();

}
