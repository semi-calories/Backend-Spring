package com.example.demo.dto.Recognizer.Request;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiOperation(
        value = "음식 사진 인식 요청",
        notes = "사용자가 입력한 음식 사진의 인식을 요청한다.")
public class RequestFoodImgRecogDto {

    private String userCode;
    private MultipartFile file;
}
