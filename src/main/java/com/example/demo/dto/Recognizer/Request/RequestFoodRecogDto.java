package com.example.demo.dto.Recognizer.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiOperation(
        value = "음식 사진 인식 요청",
        notes = "사용자가 입력한 음식 사진의 인식을 요청한다.")
public class RequestFoodRecogDto {


    private String userCode;
    private MultipartFile image;
}
