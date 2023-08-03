package com.example.demo.controller;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.dto.Recognizer.Request.RequestFoodRecogDto;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.service.DBService;
import com.example.demo.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recognizer")
public class FoodRecognizerController {
    
    private FastApiFeign fastApiFeign;
    private final DietService dietService;

    /**
     * 음식 인식용 이미지 받는 API
     */
    @PostMapping(value = "/recognizerFood", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity recogFood(@ModelAttribute RequestFoodRecogDto requestFoodRecogDto) throws IOException {
        // 음식 이미지 받아옴
        MultipartFile file = requestFoodRecogDto.getImage();
        String originalFilename = file.getOriginalFilename();

        // FAST API - AI 모델에 전송
//        ResponseFoodRecogAPIDto responseFoodRecogDto = fastApiFeign.requestRecognizer(file);


        // TODO DB에 저장!! - 비동기로
        /**
         DietRecord dietRecord = new DietRecord();
         dietService.saveFoodRecord();
         dietService.saveUserSatisfaction();
         */


        return ResponseEntity.status(HttpStatus.OK).body("test");
    }

}

