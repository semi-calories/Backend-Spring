package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserSatisfaction;
import com.example.demo.domain.User.User;
import com.example.demo.dto.Record.Request.RequestDeleteRecordDto;
import com.example.demo.dto.Record.Request.RequestRecordDto;
import com.example.demo.dto.Record.Request.RequestUpdateRecordDto;
import com.example.demo.dto.Record.Response.ResponseFoodListDto;
import com.example.demo.dto.User.Response.ResponseUserRecordDto;
import com.example.demo.dto.User.Response.UserRecordDto;
import com.example.demo.service.DBService;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    /**
     * 식단 기록 저장
     */
    @PostMapping("/text")
    public void foodRecordByText(@RequestBody @Valid RequestRecordDto requestRecordDto) throws Exception {

        // db에서 유저 검색
        User user = userService.findOne(requestRecordDto.getUserCode());
        // db에서 food 검색
        DietList food = dbService.findOne(requestRecordDto.getFoodCode());

        // entity 생성 + string 타입의 eatDate -> LocalDateTime 변경
        LocalDateTime localDateTime = getLocalDateTime(requestRecordDto.getEatDate());
        DietRecord dietRecord = new DietRecord(requestRecordDto ,localDateTime, user, food);

        // food record에 저장
        dietService.saveFoodRecord(dietRecord);

        // 만족도테이블에 만족도 저장
        UserSatisfaction userSatisfaction = new UserSatisfaction(user, food, food.getFoodName(), requestRecordDto.getSatisfaction());
        dietService.saveUserSatisfaction(user.getUserCode(), userSatisfaction);

    }

    /**
     * 식단 기록 조회
     */
    @GetMapping("/getRecord")
    public ResponseUserRecordDto getFoodRecord(Long userCode,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTime) throws Exception{
        // 식단 기록 조회
        List<DietRecord> dietRecords = dietService.findDietRecordByUserCodeAndDate(userCode, dateTime);

        ResponseUserRecordDto responseUserRecordDto = new ResponseUserRecordDto();

        // 기록에 있는 음식들 만족도 조회 -> 있으면 list에 추가
        dietRecords.forEach(dietRecord ->{
            Optional<UserSatisfaction> satisfaction = dietService.findUserSatisfaction(userCode, dietRecord.getFoodCode().getFoodCode());
            UserRecordDto userRecordDto = new UserRecordDto(dietRecord, satisfaction.orElse(null), dietRecord.getFoodCode().getFoodCode());
            responseUserRecordDto.getUserRecordDtos().add(userRecordDto);
        });

        // DTO RETURN
        return responseUserRecordDto;
    }

    /**
     * 식단 기록 삭제
     */
    @PostMapping("/deleteRecord")
    public void deleteFoodRecord(@RequestBody RequestDeleteRecordDto requestDeleteRecordDto) throws Exception {

        // string 타입의 먹은 시간 LocalDateTime으로 변경

        LocalDateTime localDateTime = getLocalDateTime(requestDeleteRecordDto.getEatDate());

        // 식단 기록 삭제
        dietService.deleteFoodRecord(requestDeleteRecordDto.getUserCode(), requestDeleteRecordDto.getFoodCode(), localDateTime);
    }

    /**
     * 식단 기록 수정
     */
    @PostMapping("/updateRecord")
    public void updateFoodRecord(@RequestBody RequestUpdateRecordDto requestUpdateRecordDto) throws Exception {

        LocalDateTime originalEatDate = getLocalDateTime(requestUpdateRecordDto.getOriginalEatDate());
        LocalDateTime newEatDate = getLocalDateTime(requestUpdateRecordDto.getEatDate());


        // 식단 기록 수정
        dietService.updateFoodRecord(requestUpdateRecordDto.getUserCode(),
                requestUpdateRecordDto.getOriginalFoodCode(),
                originalEatDate,
                newEatDate,
                requestUpdateRecordDto );

        // 만족도 수정

        // db에서 유저 검색
        User user = userService.findOne(requestUpdateRecordDto.getUserCode());
        // db에서 food 검색
        DietList food = dbService.findOne(requestUpdateRecordDto.getFoodCode());
        // 만족도 저장
        UserSatisfaction userSatisfaction = new UserSatisfaction(user, food, food.getFoodName(), requestUpdateRecordDto.getSatisfaction());
        dietService.saveUserSatisfaction(requestUpdateRecordDto.getUserCode(), userSatisfaction);

    }


    /**
     * 음식명으로 음식 조회
     */
    @GetMapping("/foodSearch")
    public ResponseFoodListDto foodSearchByText(String foodName){

        List<DietList> dietListByName = dbService.findDietListByName(foodName);
        return new ResponseFoodListDto(dietListByName);
    }


    private static LocalDateTime getLocalDateTime(String date) {
        String[] eatDateList = date.split("T");
        String[] dateList = eatDateList[0].split("-"); // "2023-09-11"
        String[] timeList = eatDateList[1].split(":");// "13:11"
        LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(dateList[0]), Integer.parseInt(dateList[1]), Integer.parseInt(dateList[2]), Integer.parseInt(timeList[0]), Integer.parseInt(timeList[1]),Integer.parseInt(timeList[2]));
        return localDateTime;
    }


    @AllArgsConstructor
    @Getter
    static class ReturnDto<T>{
        private T response;
    }
}
