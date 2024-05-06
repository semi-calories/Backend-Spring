package com.example.demo.controller;

import com.example.demo.domain.User.UserGoal;
import com.example.demo.domain.User.UserWeight;
import com.example.demo.dto.Record.Request.RequestSavePredictWeightDto;
import com.example.demo.dto.Record.Request.RequestWeekStatDto;
import com.example.demo.dto.Record.Request.RequestWeightDeleteDto;
import com.example.demo.dto.Record.Request.RequestWeightSaveDto;
import com.example.demo.dto.Record.Response.ResponseMonthStatsDto;
import com.example.demo.dto.Record.Response.ResponseWeekStatsDto;
import com.example.demo.dto.Record.Response.ResponseWeightRangeDto;
import com.example.demo.dto.Record.Response.WeightDto;
import com.example.demo.service.DBService;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/statistics")
public class StatisticsController {

    private final DietService dietService;
    private final UserService userService;


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

        // 유저 몸무게 조회
        List<UserWeight> monthWeight = userService.getMonthRangeWeight(userCode, startYear, startMonth, startDay, endYear, endMonth, endDay);

        // 동일한 날짜의 예상 몸무게 조회
        List<WeightDto> sameTimeWeight = userService.getSameTimeWeight(userCode, monthWeight);

        return new ResponseWeightRangeDto(sameTimeWeight);
    }

    /**
     * 예상 몸무게 저장
     */
    @PostMapping("/savePredictWeight")
    public void savePredictWeight(@RequestBody RequestSavePredictWeightDto requestSavePredictWeightDto){

        // 유저 목표 몸무게 수정
        userService.userGoalWeightUpdate(requestSavePredictWeightDto.getUserCode(), requestSavePredictWeightDto.getGoalWeight(), requestSavePredictWeightDto.getPeriod());

        // 헤리스 베네딕트 수정
        userService.changeHarrisBenedict(requestSavePredictWeightDto.getUserCode(),requestSavePredictWeightDto.getGoalWeight());

        // 유저 예상 몸무게 추이 저장
        userService.savePredictWeight(requestSavePredictWeightDto.getUserCode(), requestSavePredictWeightDto.getPeriod());

    }

    private static LocalDateTime getLocalDateTime(String date) {
        String[] eatDateList = date.split("T");
        String[] dateList = eatDateList[0].split("-"); // "2023-09-11"
        String[] timeList = eatDateList[1].split(":");// "13:11"
        return LocalDateTime.of(Integer.parseInt(dateList[0]), Integer.parseInt(dateList[1]), Integer.parseInt(dateList[2]), Integer.parseInt(timeList[0]), Integer.parseInt(timeList[1]),Integer.parseInt(timeList[2]));
    }

    @AllArgsConstructor
    @Getter
    static class ReturnDto<T>{
        private T response;
    }
}
