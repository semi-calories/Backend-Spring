package com.example.demo.service;

import com.example.demo.domain.User.Alert.AlertRecord;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.repository.AlertRecordRepository;
import com.example.demo.repository.AlertSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertSendService {
    private final AlertSettingRepository alertSettingRepository;
    private final AlertRecordRepository alertRecordRepository;

    private final FastApiFeign fastApiFeign;
    private final UserService userService;
    private final DietService dietService;
    private final DBService dbService;

    /**
     * 10초 후 실행
     */
    @Transactional
    //@Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void findALlAlertSetting(){
        // 한국 시간대 설정
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");

        // 지금 날짜 구하기 (한국 시간 기준)
        LocalDateTime now = LocalDateTime.now(koreaZoneId);

        // DateTimeFormatter를 사용하여 LocalDateTime을 문자열로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        List<AlertRecord> alertRecordList = alertRecordRepository.findAllByAlertStatusAndAlertDate(false, formattedDateTime);

    }

}