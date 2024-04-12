package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.dto.Recognizer.FastAPI.ResponseFoodRecogAPIDto;
import com.example.demo.dto.Recognizer.Request.RequestFoodImgRecogDto;
import com.example.demo.dto.Recognizer.Request.RequestFoodRecogDto;
import com.example.demo.dto.Recognizer.Response.ResponseFoodRecogDto;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.service.DBService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recognizer")
public class FoodRecognizerController {
    
    private final FastApiFeign fastApiFeign;
    private final DBService dbService;

    /**
     * 음식 인식용 이미지 받는 API
     */
    /**
    @PostMapping(value = "/recognizerFood")
    public ResponseEntity recogFood(@RequestBody RequestFoodRecogDto requestFoodRecogDto) {

        // base64 -> MultipartFile 생성
        String fileName = "temp";
        MultipartFile multipartFile = Base64ToMultipartFileConverter.getMultipartFile(requestFoodRecogDto.getFile(), fileName);

        // 사진 인식 응답 DTO 생성
        try {
            // FAST API - 음식 사진 AI 모델에 전송
            ResponseFoodRecogAPIDto responseFoodRecogAPIDto = fastApiFeign.requestRecognizer(multipartFile);

            // db에 값 조회해 영양성분 get
            List<DietList> dietLists = dbService.findByList(responseFoodRecogAPIDto.getFoodCodeList());
            ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto(dietLists);
            return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);
        } catch (Exception e) {
            ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto();
            return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);

        }
    }
    **/

    /**
     * 음식 인식용 이미지 받는 API
     */
    @PostMapping(value = "/recognizerFoodImg")
    public ResponseEntity recogMultipartFoodImg(@ModelAttribute RequestFoodImgRecogDto requestFoodImgRecogDto) {

        // 사진 인식 응답 DTO 생성
        try {
            // FAST API - 음식 사진 AI 모델에 전송
            ResponseFoodRecogAPIDto responseFoodRecogAPIDto = fastApiFeign.requestRecognizer(requestFoodImgRecogDto.getFile());

            // db에 값 조회해 영양성분 get
            List<DietList> dietLists = dbService.findByList(responseFoodRecogAPIDto.getFoodCodeList());
            ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto(dietLists);
            return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);
        } catch (Exception e) {
            ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto();
            return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);

        }
    }
}

