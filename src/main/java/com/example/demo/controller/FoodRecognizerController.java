package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.dto.Recognizer.FastAPI.ResponseFoodRecogAPIDto;
import com.example.demo.dto.Recognizer.Request.RequestFoodRecogDto;
import com.example.demo.dto.Recognizer.Response.ResponseFoodRecogDto;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.service.DBService;
import com.example.demo.service.DietService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.DefaultFileItem;
import org.apache.commons.fileupload.FileItem;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
<<<<<<< HEAD

import java.io.IOException;
=======
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.*;
import java.util.Base64;
>>>>>>> 44c5987cbf32614baf0d624cef1fd6ef9b4853e4
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recognizer")
public class FoodRecognizerController {
    
    private final FastApiFeign fastApiFeign;
    private final DBService dbService;
    private final Base64ToMultipartFileConverter base64ToMultipartFileConverter;

    /**
     * 음식 인식용 이미지 받는 API
     */
    @PostMapping(value = "/recognizerFood")
    public ResponseEntity recogFood(@RequestBody RequestFoodRecogDto requestFoodRecogDto) throws IOException {

        // base64 -> MultipartFile 생성
        String fileName = "temp";
        MultipartFile multipartFile = base64ToMultipartFileConverter.getMultipartFile(requestFoodRecogDto.getFile(), fileName);

        // FAST API - 음식 사진 AI 모델에 전송
        ResponseFoodRecogAPIDto responseFoodRecogAPIDto = fastApiFeign.requestRecognizer(multipartFile);

        // db에 값 조회해 영양성분 get
        List<DietList> dietLists = dbService.findByList(responseFoodRecogAPIDto.getFoodCodeList());

        // 사진 인식 응답 DTO 생성
        try {
            ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto(dietLists);
            return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);
        } catch (Exception e) {
            ResponseFoodRecogDto responseFoodRecogDto = new ResponseFoodRecogDto();
            return ResponseEntity.status(HttpStatus.OK).body(responseFoodRecogDto);

        }
    }
}

