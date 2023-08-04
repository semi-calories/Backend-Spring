package com.example.demo.dto.Recognizer.FastAPI;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
@ApiOperation(
        value = "음식 사진 인식 FastAPI 요청",
        notes = "FastAPI에 음식 사진의 인식을 요청한다.")
public class RequestFoodRecogAPIDto {
}
