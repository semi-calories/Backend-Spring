package com.example.demo.controller;

import com.example.demo.dto.Recognizer.Logmeal.ResponseDto.Recognition_results;
import com.example.demo.dto.Recognizer.Logmeal.ResponseDto.ResponseLogmealDto;
import com.example.demo.dto.Recognizer.Request.RequestFoodRecogDto;
import com.example.demo.dto.Recognizer.Response.ResponseFoodRecogDto;
import com.example.demo.feign.LogmealApiFeign;
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

    private final LogmealApiFeign logmealApiFeign;
    String auth = "Bearer f45c34959ac63312f2efeb272f3ec28f4d75a46e";

    @PostMapping(value = "/recognizerFood", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity recogFood(@ModelAttribute RequestFoodRecogDto requestFoodRecogDto) throws IOException {
        // 음식 이미지 받아옴
        MultipartFile file = requestFoodRecogDto.getImage();

        // logmeal에 api 전송
        ResponseLogmealDto logmealList = logmealApiFeign.getFoodName(auth, "eng", file);

        // 결과 값 받아와 name list 생성
        List<List<Recognition_results>> collect = logmealList.getSegmentation_results().stream().map(list -> list.getRecognition_results()).collect(Collectors.toList());
        ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto(collect);

        // TODO DB에 저장!! - 비동기로

        return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);
    }

}

