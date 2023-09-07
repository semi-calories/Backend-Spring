package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.dto.Recognizer.FastAPI.ResponseFoodRecogAPIDto;
import com.example.demo.dto.Recognizer.Response.ResponseFoodRecogDto;
import com.example.demo.feign.FastApiFeign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class testController {

    private final FastApiFeign fastApiFeign;

    @Retryable(maxAttempts = 3)
    @GetMapping("/api")
    public String testFAST(){
        log.info("####################### home api");
        System.out.println(fastApiFeign.test());

        log.info("###################### home");
        return "연결 성공";
    }



    @GetMapping("/test")
    public int test(){
        return 2023;
    }


    /**
     * 음식 인식용 이미지 받는 API
     */
    @PostMapping(value = "/imageTest")
    public void recogFood( @RequestParam Base64 file) throws IOException {
        System.out.println("file = " + file);
    }

}
