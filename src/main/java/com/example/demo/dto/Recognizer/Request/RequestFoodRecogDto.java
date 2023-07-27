package com.example.demo.dto.Recognizer.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestFoodRecogDto {

    private String userCode;
    private MultipartFile image;
}
