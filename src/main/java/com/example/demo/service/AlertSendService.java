package com.example.demo.service;

import java.time.temporal.ChronoUnit;
import com.example.demo.domain.Alert.AlertRecord;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.repository.AlertRecordRepository;
import com.example.demo.repository.AlertSettingRepository;
import com.example.demo.config.NotificationConfig.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    private final RestTemplate restTemplate;


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

        // 1분 뒤
        LocalDateTime nextMinute = now.plus(1, ChronoUnit.MINUTES);

        // DateTimeFormatter를 사용하여 LocalDateTime을 문자열로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        String formattedNext = nextMinute.format(formatter);

        List<AlertRecord> alertRecordList = alertRecordRepository.findAllByAlertStatusWithAlertDateBetween(false, formattedNow, formattedNext);

        for (int i=0; i<alertRecordList.size(); i++){
            if(sendExpoPushNotification(alertRecordList.get(i))){
                //발송이 잘 된 것
                //alertRecordRepository.updateStatusByUserCodeWithAlertDateBetween(true, alertRecordList.get(i).getUserCode().getUserCode(), formattedNow, formattedNext);
            } else {
                // TODO : ERROR MESSAGE DB에 넣기
            }
        }

    }
    private boolean sendExpoPushNotification(AlertRecord alertRecord) {
        // Expo push notification을 보내기 위한 Expo 서버 API 엔드포인트
        String expoApiEndpoint = "https://exp.host/--/api/v2/push/send";



        String month = alertRecord.getAlertDate();
        String title = String.format("%s월 %일 % %시 %분");
        //→ Message Title : MM월 dd일 (아/점/저) N시 N분
        String body = String.format("%s %s kcal");
        //→ Message Body : 음식이름 칼로리 kcal


        // Expo 서버에 전송할 데이터 (푸시 알림 내용 등)
        String expoRequestBody = String.format("{ \"to\": \"%s\", \"title\": \"%s\", \"body\": \"%s\" }",
                alertRecord.getUserToken(), title, body);

        // Expo 서버로 HTTP POST 요청을 보냄
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(expoRequestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate().exchange(expoApiEndpoint, HttpMethod.POST, requestEntity, String.class);

        // Expo 서버 응답 확인
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Expo push notification sent successfully");
            return true;
        }
        System.err.println("Failed to send Expo push notification. Response: " + responseEntity.getBody());
        return false;
    }
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
/*    private static <list> int getLocalDateTime(String date) {
        // 2023-11-14 07:00:00
        String[] eatDateList = date.split("T");
        String[] dateList = eatDateList[0].split("-"); // "2023-09-11"
        String[] timeList = eatDateList[1].split(":");// "13:11"
        LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(dateList[0]), Integer.parseInt(dateList[1]), Integer.parseInt(dateList[2]), Integer.parseInt(timeList[0]), Integer.parseInt(timeList[1]),Integer.parseInt(timeList[2]));
        return Integer.parseInt(dateList[1]), Integer.parseInt(dateList[2]) ;
    }*/
}