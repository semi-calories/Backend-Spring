package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserSatisfaction;
import com.example.demo.domain.User.User;
import com.example.demo.dto.Record.Request.RequestRecordDto;
import com.example.demo.dto.Record.Response.ResponseFoodListDto;
import com.example.demo.dto.User.Response.ResponseUserRecordDto;
import com.example.demo.dto.User.Response.UserRecordDto;
import com.example.demo.service.DBService;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        // food record에 저장
        dietService.saveFoodRecord(dietRecord);

        // 만족도테이블에 만족도 저장 TODO: 만족도는 해당음식에서 하나만 유지
        UserSatisfaction userSatisfaction = new UserSatisfaction(user, food, requestRecordDto.getFoodName(), requestRecordDto.getSatisfaction());
        dietService.saveUserSatisfaction(userSatisfaction);

        return ResponseEntity.status(HttpStatus.OK).body("식단 기록 저장 완료");
    }

    @GetMapping("/get-record")
    public ResponseUserRecordDto getFoodRecord(Long userCode, LocalDateTime dateTime) throws Exception{

        // 식단 기록 조회
        List<DietRecord> dietRecords = dietService.findDietRecordByUserCodeAndDate(userCode, dateTime);


        ResponseUserRecordDto responseUserRecordDto = new ResponseUserRecordDto();

        // TODO 리팩토링 필요
        // 기록에 있는 음식들 만족도 조회 -> 있으면 list에 추가
        List<UserSatisfaction> userSatisfactionList = new ArrayList<>();
        for (DietRecord dietRecord : dietRecords) {
            Optional<UserSatisfaction> satisfaction = dietService.findUserSatisfaction(userCode, dietRecord.getFoodCode().getFoodCode());
            if (satisfaction.isPresent()){
                UserRecordDto userRecordDto = new UserRecordDto(dietRecord,satisfaction.get(), dietRecord.getFoodCode().getFoodCode());
                responseUserRecordDto.getUserRecordDtos().add(userRecordDto);
            }else{
                UserRecordDto userRecordDto = new UserRecordDto(dietRecord,null, dietRecord.getFoodCode().getFoodCode());
                responseUserRecordDto.getUserRecordDtos().add(userRecordDto);
            }
        }

        // DTO RETURN

        return responseUserRecordDto;
    }

    @GetMapping("/food-search")
    public ResponseFoodListDto foodSearchByText(String foodName){

        List<DietList> dietListByName = dbService.findDietListByName(foodName);
        return new ResponseFoodListDto(dietListByName);
    }
}
