package com.example.demo.controller;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserSatisfaction;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.domain.User.UserWeight;
import com.example.demo.dto.Record.Request.*;
import com.example.demo.dto.Record.Response.*;
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
    public ReturnDto foodRecordByText(@RequestBody @Valid RequestRecordDto requestRecordDto) throws Exception {

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

        return new ReturnDto<>(true);
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
    public ReturnDto deleteFoodRecord(@RequestBody RequestDeleteRecordDto requestDeleteRecordDto) throws Exception {

        // string 타입의 먹은 시간 LocalDateTime으로 변경

        LocalDateTime localDateTime = getLocalDateTime(requestDeleteRecordDto.getEatDate());

        // 식단 기록 삭제
        dietService.deleteFoodRecord(requestDeleteRecordDto.getUserCode(), requestDeleteRecordDto.getFoodCode(), localDateTime);
        return new ReturnDto(true);
    }

    /**
     * 식단 기록 수정
     */
    @PostMapping("/updateRecord")
    public ReturnDto updateFoodRecord(@RequestBody RequestUpdateRecordDto requestUpdateRecordDto) throws Exception {

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

        return new ReturnDto<>(true);
    }


    /**
     * 음식명으로 음식 조회
     */
    @GetMapping("/foodSearch")
    public ResponseFoodListDto foodSearchByText(String foodName){

        List<DietList> dietListByName = dbService.findDietListByName(foodName);
        return new ResponseFoodListDto(dietListByName);
    }

    /**
     * 월간 통계 조회
     */
    @GetMapping("/getMonthStats")
    public ResponseMonthStatsDto getMonthStats(Long userCode, int year) throws Exception {

        // 유저 목표 조회
        UserGoal userGoal = userService.findUserWithUserGoal(userCode);

        // 월간 통계 조회
        List<List<Double>> monthList = dietService.getMonthList(userGoal, year);

        return new ResponseMonthStatsDto(monthList);
    }

    /**
     * 주간 통계 조회
     */
    @PostMapping("/getWeekStats")
    public ResponseWeekStatsDto getWeekStats(Long userCode, @RequestBody RequestWeekStatDto requestWeekStatDto) throws Exception{

        // 유저 목표 조회
        UserGoal userGoal = userService.findUserWithUserGoal(userCode);

        // 주간 통계 조회
        List<List<Double>> weekList = dietService.getWeekList(userGoal, requestWeekStatDto);

        return new ResponseWeekStatsDto(weekList);
    }

    /**
     * 유저 몸무게 저장/수정
     */
    @PostMapping("/saveWeight")
    public void saveWeight(@RequestBody RequestWeightSaveDto requestSaveWeightDto) throws Exception {

        // 몸무게 테이블에 저장
        LocalDateTime dateTime = getLocalDateTime(requestSaveWeightDto.getTimestamp());
        userService.saveUserWeight(requestSaveWeightDto.getUserCode(),dateTime,requestSaveWeightDto.getUserWeight());
    }

    /**
     * 유저 몸무게 삭제
     */
    @PostMapping("/deleteWeight")
    public void deleteWeight(@RequestBody RequestWeightDeleteDto requestWeightDeleteDto) throws Exception {

        // 몸무게 테이블에서 삭제
        LocalDateTime dateTime = getLocalDateTime(requestWeightDeleteDto.getTimestamp());
        userService.deleteUserWeight(requestWeightDeleteDto.getUserCode(), dateTime);
    }

    /**
     * 유저 몸무게 찾기
     */
    @GetMapping("/getWeight")
    public ReturnDto getWeight(Long userCode, String timestamp){
        LocalDateTime dateTime = getLocalDateTime(timestamp);
        List<UserWeight> weightList = userService.getUserWeight(userCode, LocalDate.from(dateTime));
        if(!weightList.isEmpty()){
            return new ReturnDto(weightList.get(0));
        }else{
            return new ReturnDto(0);
        }

    }

    /**
     * 유저 몸무게 기간으로 찾기
     */
    @GetMapping("/getMonthRangeWeight")
    public ResponseWeightRangeDto getMonthRangeWeight(Long userCode, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay ){
        List<UserWeight> monthWeight = userService.getMonthRangeWeight(userCode, startYear, startMonth, startDay, endYear, endMonth, endDay);
        return new ResponseWeightRangeDto(monthWeight);
    }

    /**
     * 예상 몸무게 저장
     */
    @PostMapping("/savePredictWeight")
    public void savePredictWeight(@RequestBody RequestSavePredictWeightDto requestSavePredictWeightDto){

        System.out.println("######################## " + requestSavePredictWeightDto);

        // 유저 목표 몸무게 수정
        userService.userGoalWeightUpdate(requestSavePredictWeightDto.getUserCode(), requestSavePredictWeightDto.getGoalWeight(), requestSavePredictWeightDto.getPeriod());

        // 헤리스 베네딕트 수정
        userService.changeHarrisBenedict(requestSavePredictWeightDto.getUserCode(),requestSavePredictWeightDto.getGoalWeight());

        // 유저 예상 몸무게 추이 저장
        userService.savePredictWeight(requestSavePredictWeightDto.getUserCode(), requestSavePredictWeightDto.getPeriod());

    }

    /**
     * 유저 예상 몸무게 조회
     */
    @GetMapping("/getPredictWeight")
    public ResponsePredictWeightDto getPredictWeight(Long userCode){
        return new ResponsePredictWeightDto(userService.getPredictWeight(userCode));
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
