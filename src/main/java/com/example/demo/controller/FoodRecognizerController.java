package com.example.demo.controller;

import com.example.demo.dto.Recognizer.Request.RequestFoodRecogDto;
import com.example.demo.dto.Recognizer.Response.ResponseFoodRecogDto;
import com.example.demo.feign.FastApiFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recognizer")
public class FoodRecognizerController {
    
    private FastApiFeign fastApiFeign;

    @PostMapping(value = "/recognizerFood", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity recogFood(@ModelAttribute RequestFoodRecogDto requestFoodRecogDto) throws IOException {
        // 음식 이미지 받아옴
        MultipartFile file = requestFoodRecogDto.getImage();

        // logmeal에 api 전송
        ResponseFoodRecogDto responseFoodRecogDto = fastApiFeign.requestRecognizer(file);


        // TODO DB에 저장!! - 비동기로

        return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);
    }

}

