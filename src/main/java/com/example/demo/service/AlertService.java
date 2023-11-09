package com.example.demo.service;

import com.example.demo.domain.User.Alert.AlertRecord;
import com.example.demo.domain.User.Alert.AlertSetting;
import com.example.demo.domain.User.User;
import com.example.demo.dto.Alert.Request.RequestUpdateAlertSettingDto;
import com.example.demo.repository.AlertRecordRepository;
import com.example.demo.repository.AlertSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertService {
    private final AlertSettingRepository alertSettingRepository;
    private final AlertRecordRepository alertRecordRepository;

    /**
     * 푸시 알람 수신 허용
     */
    @Transactional
    public User saveAlertSetting(AlertSetting alertSetting){
        alertSettingRepository.save(alertSetting);
        return alertSetting.getUserCode();
    }

    /**
     * 유저 설정 단건 검색
     */
    public AlertSetting findOne(Long userCode){
        return alertSettingRepository.findByUserCode(userCode)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 설정입니다."));
    }

    /**
     * 푸시 알람 설정 시간 수정
     */
    @Transactional
    public AlertSetting updateAlertSetting(RequestUpdateAlertSettingDto requestUpdateAlertSettingDto){
        AlertSetting alertSetting = findOne(requestUpdateAlertSettingDto.getUserCode());
        alertSetting.changeSetting(requestUpdateAlertSettingDto.isSetting(),
                requestUpdateAlertSettingDto.getBreakfastHour(), requestUpdateAlertSettingDto.getBreakfastMinute(),
                requestUpdateAlertSettingDto.getLaunchHour(), requestUpdateAlertSettingDto.getLaunchMinute(),
                requestUpdateAlertSettingDto.getDinnerHour(), requestUpdateAlertSettingDto.getDinnerMinute());
        return alertSetting;
    }


    /**
     * 푸시 알람 발송 기록 1주일치
     */
    public List<AlertRecord> getRangeRecord(User user, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        
        LocalDateTime startDatetime = LocalDateTime.of(startYear,startMonth,startDay,0,0);
        LocalDateTime endDatetime = LocalDateTime.of(endYear,endMonth,endDay,23,59);

        List<AlertRecord> alertRecordList = alertRecordRepository.findAllByUserCodeAndAlertStatusWithAlertDateBetween(user, 1, startDatetime, endDatetime);
        return alertRecordList;
    }
}
