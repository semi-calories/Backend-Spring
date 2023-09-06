package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.dto.Recognizer.FastAPI.ResponseFoodRecogAPIDto;
import com.example.demo.dto.Recognizer.Request.RequestFoodRecogDto;
import com.example.demo.dto.Recognizer.Response.ResponseFoodRecogDto;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recognizer")
public class FoodRecognizerController {
    
    private final FastApiFeign fastApiFeign;
    private final DBService dbService;
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
        ResponseFoodRecogAPIDto responseFoodRecogAPIDto = fastApiFeign.requestRecognizer(file);

        // db에 값 조회해 영양성분 get
        List<DietList> dietLists = dbService.findByList(responseFoodRecogAPIDto.getFoodCodeList());

        // 사진 인식 응답 DTO 생성
        ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto(dietLists);

        return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);
    }

}

