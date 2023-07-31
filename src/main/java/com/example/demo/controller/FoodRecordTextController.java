package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserSatisfaction;
import com.example.demo.domain.User;
import com.example.demo.dto.Record.Request.RequestRecordDto;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.service.DBService;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
        @RequiredArgsConstructor
@Slf4j
        @RequestMapping("/record")
public class FoodRecordTextController {

    private final DietService dietService;
    private final UserService userService;
    private final DBService dbService;

    @PostMapping("/text")
    public ResponseEntity foodRecordByText(@RequestBody RequestRecordDto requestRecordDto) throws Exception {

        // db에서 유저 검색
        User user = userService.findOne(requestRecordDto.getUserCode());
        // db에서 food 검색
        DietList food = dbService.findOne(requestRecordDto.getFoodCode());

        // entity 생성
        DietRecord dietRecord = new DietRecord(requestRecordDto , user, food);

        // food record에 다 저장하고
        dietService.saveFoodRecordByText(dietRecord);

        // 만족도테이블에 만족도 저장
        UserSatisfaction userSatisfaction = new UserSatisfaction(user, food, requestRecordDto.getFoodName(), requestRecordDto.getSatisfaction());
        dietService.saveUserSatisfaction(userSatisfaction);

        return ResponseEntity.status(HttpStatus.OK).body("식단 기록 저장 완료");
    }
}
