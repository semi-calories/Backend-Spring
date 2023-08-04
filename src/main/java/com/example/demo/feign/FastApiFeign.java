package com.example.demo.feign;

import com.example.demo.dto.Recognizer.FastAPI.ResponseFoodRecogAPIDto;
import com.example.demo.dto.Recommend.FastAPI.RequestRecommendAPIDto;
import com.example.demo.dto.Recommend.FastAPI.ResponseRecommendAPIDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="FastApiFeign", url="http://localhost:8000")
public interface FastApiFeign {

    /**
     * test 연결
     */
    @GetMapping("/test")
    public ResponseRecommendAPIDto test();

    /**
     * 추천 알고리즘 요청
     */
    @PostMapping("/request-recommend")
    public ResponseRecommendAPIDto requestRecommend(
            @RequestBody RequestRecommendAPIDto requestRecommendAPIDto
            );

    /**
     * 음식 인식 요청
     */
    @PostMapping("/request-food-recog")
    public ResponseFoodRecogAPIDto requestRecognizer(
            @ModelAttribute MultipartFile file
            );

}