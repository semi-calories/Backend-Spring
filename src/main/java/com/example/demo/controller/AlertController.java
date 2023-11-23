package com.example.demo.controller;

import com.example.demo.domain.Alert.AlertRecord;
import com.example.demo.domain.Alert.AlertSetting;
import com.example.demo.domain.User.User;
import com.example.demo.dto.Alert.Request.RequestAlertDto;
import com.example.demo.dto.Alert.Request.RequestUpdateAlertSettingDto;
import com.example.demo.dto.Alert.Response.ResponseGetAlertRecordListDto;
import com.example.demo.dto.Alert.Response.ResponseGetAlertSettingDto;
import com.example.demo.service.AlertService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/alert")
public class AlertController {
    private final AlertService alertService;
    private final UserService userService;

    /**
     * 푸시 알람 수신 허용
     */
    @PostMapping("/saveSetting")
    public AlertController.ReturnDto saveAlertSetting(@RequestBody RequestAlertDto requestAlertDto) throws Exception {

        // db에서 유저 검색
        User user = userService.findOne(requestAlertDto.getUserCode());

        // entity 생성
        AlertSetting alertSetting = new AlertSetting(user, requestAlertDto.getUserToken(), requestAlertDto.isSetting());

        // alert setting 에 저장
        alertService.saveAlertSetting(alertSetting);

        return new AlertController.ReturnDto<>(true);
    }

    /**
     * 푸시 알람 설정 조회
     */
    @PostMapping("/getSetting")
    public ResponseGetAlertSettingDto getAlertSetting(@RequestBody Long userCode) throws Exception {
        // 기본 정보 조회
        AlertSetting alertSetting = alertService.findOne(userCode);
        System.out.println(alertSetting.getUserToken());
        System.out.println(alertSetting.getUserCode());

        // 응답 DTO 생성
        ResponseGetAlertSettingDto responseGetAlertSettingDto = new ResponseGetAlertSettingDto(alertSetting);
        log.info("################################ responseGetAlertSettingDto.getUserToken() {}", responseGetAlertSettingDto );


        return responseGetAlertSettingDto;
    }


    /**
     * 푸시 알람 수신 시간 or 수신 여부 설정 변경
     */
    @PostMapping("/updateSetting")
    public ReturnDto updateAlertSetting(@RequestBody RequestUpdateAlertSettingDto requestUpdateAlertSettingDto) throws Exception {
        // 푸시 알림 시간 설정 변경
        alertService.updateAlertSetting(requestUpdateAlertSettingDto);

        return new AlertController.ReturnDto<>(true);
    }


    /**
     * 푸시 알람 발송 기록 조회
     */
    @GetMapping("/getAlertRecord")
    public ResponseGetAlertRecordListDto getAlertRecord(Long userCode, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) throws Exception {
        // db에서 유저 검색
        User user = userService.findOne(userCode);

        // 기본 정보 조회
        List<AlertRecord> alertRecordList = alertService.getRangeRecord(userCode, startYear, startMonth, startDay, endYear, endMonth, endDay);

        // 응답 DTO 생성
        return new ResponseGetAlertRecordListDto(alertRecordList);
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
// 푸시 알람 설정 조회
/*    @PostMapping("/getAlertSet")
    public AlertController.ReturnDto test(Long userCode){
        System.out.println("########################## test");
        return new AlertController.ReturnDto<>(true);
    }*/