package com.example.demo.dto.Alert.Response;

import com.example.demo.domain.User.Alert.AlertSetting;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation(
        value = "푸시 알림 설정 조회 응답",
        notes = "푸시 알림 설정 조회 요청에 응답한다.")

@ToString
public class ResponseGetAlertSettingDto {

    private Long userCode;
    private String userToken;
    private boolean setting;
    private int breakfastHour;
    private int breakfastMinute;
    private int launchHour;
    private int launchMinute;
    private int dinnerHour;
    private int dinnerMinute;

    public ResponseGetAlertSettingDto(AlertSetting alertSetting) {
        this.userCode = alertSetting.getUserCode().getUserCode();
        this.userToken = alertSetting.getUserToken();
        this.setting = alertSetting.isSetting();
        this.breakfastHour = alertSetting.getBreakfastHour();
        this.breakfastMinute=alertSetting.getBreakfastMinute();
        this.launchHour=alertSetting.getLaunchHour();
        this.launchMinute=alertSetting.getLaunchMinute();
        this.dinnerHour=alertSetting.getDinnerHour();
        this.dinnerMinute=alertSetting.getDinnerMinute();
    }
}
