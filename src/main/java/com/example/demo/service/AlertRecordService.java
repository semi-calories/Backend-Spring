//package com.example.demo.service;
//
//import com.example.demo.domain.Diet.DietRecord;
//import com.example.demo.domain.Diet.UserDietDislike;
//import com.example.demo.domain.Diet.UserDietPrefer;
//import com.example.demo.domain.Alert.AlertRecord;
//import com.example.demo.domain.Alert.AlertSetting;
//import com.example.demo.domain.User.UserGoal;
//import com.example.demo.dto.Recommend.FastAPI.RequestRecommendAPIDto;
//import com.example.demo.dto.Recommend.FastAPI.ResponseRecommendAPIDto;
//import com.example.demo.dto.Recommend.Response.RecommendDto;
//import com.example.demo.feign.FastApiFeign;
//import com.example.demo.repository.AlertRecordRepository;
//import com.example.demo.repository.AlertSettingRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.time.LocalDateTime;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class AlertRecordService {
//    private final AlertSettingRepository alertSettingRepository;
//    private final AlertRecordRepository alertRecordRepository;
//
//    private final FastApiFeign fastApiFeign;
//    private final UserService userService;
//    private final DietService dietService;
//    private final DBService dbService;
//
//
//    /**
//     * 매일 20시 00분에 실행
//     */
//    @Transactional
//    @Scheduled(cron = "0 0 20 * * ?", zone="Asia/Seoul")
//    public void findALlAlertSetting(){
//
//        // 1. 허용여부 및 수신시간 확인
//        List<AlertSetting> alertSettingList = alertSettingRepository.findAllBySetting(true);
//
//        // 2. 추천 요청
//        // 3. AlertRecord 저장
//        List<AlertRecord> alertRecordList = new ArrayList<>();
//        for(int i=0; i<alertSettingList.size(); i++){
//            Long userCode = alertSettingList.get(i).getUserCode().getUserCode();
//            for(int j=1; j<4; j++){
//                RecommendDto recommend = requestPushRecommend(userCode, j).get(0);
//                alertRecordList.add(getAlertRecord(alertSettingList.get(i), j, recommend));
//            }
//        }
//        alertRecordRepository.saveAll(alertRecordList);
//    }
//
//
//
//    public AlertRecord getAlertRecord(AlertSetting alertSetting, int j, RecommendDto recommend ){
//        if(j == 1) {
//
//            return new AlertRecord(alertSetting.getUserCode(), alertSetting.getUserToken(),
//                    getTomorrowDateTime(alertSetting.getBreakfastHour(), alertSetting.getBreakfastMinute()), alertSetting.getBreakfastHour(), alertSetting.getBreakfastMinute(),
//                    j, dbService.findOne(Long.valueOf(recommend.getFoodCode())),
//                    recommend.getFoodName(), recommend.getFoodKcal(),
//                    recommend.getFoodCarbon(), recommend.getFoodProtein(), recommend.getFoodFat());
//        }
//        if(j == 2) {
//            return new AlertRecord(alertSetting.getUserCode(), alertSetting.getUserToken(),
//                    getTomorrowDateTime(alertSetting.getLunchHour(), alertSetting.getLunchMinute()), alertSetting.getLunchHour(), alertSetting.getLunchMinute(),
//                    j, dbService.findOne(Long.valueOf(recommend.getFoodCode())),
//                    recommend.getFoodName(), recommend.getFoodKcal(),
//                    recommend.getFoodCarbon(), recommend.getFoodProtein(), recommend.getFoodFat());
//        }
//        return new AlertRecord(alertSetting.getUserCode(), alertSetting.getUserToken(),
//                getTomorrowDateTime(alertSetting.getDinnerHour(), alertSetting.getDinnerMinute()), alertSetting.getDinnerHour(), alertSetting.getDinnerMinute(),
//                j, dbService.findOne(Long.valueOf(recommend.getFoodCode())),
//                recommend.getFoodName(), recommend.getFoodKcal(),
//                recommend.getFoodCarbon(), recommend.getFoodProtein(), recommend.getFoodFat());
//    }
//
//    public List<RecommendDto> requestPushRecommend(Long UserCode, int eatTimes) {
//
//        // DB에서 해당 정보 가져옴
//        // 유저 목표 및 유저 조회
//        UserGoal user = userService.findUserWithUserGoal(UserCode);
//
//        // 유저 선호, 비선호, 기록 조회
//        List<UserDietPrefer> preferDiet = dietService.findPreferByUserCode(UserCode);
//        List<UserDietDislike> dislikeDiet = dietService.findDislikeByUserCode(UserCode);
//        List<DietRecord> dietRecords = dietService.findDietRecordByUserCodeAndDate(UserCode, LocalDate.now());
//
//
//        // FASTAPI 서버에 api 요청
//        RequestRecommendAPIDto requestRecommendAPIDto =
//                new RequestRecommendAPIDto(user, eatTimes, preferDiet, dislikeDiet, dietRecords);
//
//        ResponseRecommendAPIDto responseAPIDto = fastApiFeign.requestRecommend(requestRecommendAPIDto);
//
//        // FASTAPI 응답 DTO로 list별로 음식 접근가능케 함 (fast api: 인덱스별로 접근)
//        List<RecommendDto> recommendDtoList = IntStream.range(0, responseAPIDto.getFoodCodeList().size()) // 음식 추천 수만큼 반복
//                .mapToObj(i -> new RecommendDto(
//                        responseAPIDto.getFoodCodeList().get(i),
//                        responseAPIDto.getFoodNameList().get(i),
//                        responseAPIDto.getFoodMainCategoryList().get(i),
//                        responseAPIDto.getFoodDetailedClassificationList().get(i),
//                        responseAPIDto.getFoodWeightList().get(i),
//                        responseAPIDto.getFoodKcalList().get(i),
//                        responseAPIDto.getFoodCarbonList().get(i),
//                        responseAPIDto.getFoodProteinList().get(i),
//                        responseAPIDto.getFoodFatList().get(i)
//                ))
//                .collect(Collectors.toList());
//
//        // 응답 DTO 생성 및 return
//        return recommendDtoList;
//    }
//
//    public String getTomorrowDateTime(int hour, int minute){
//        // 한국 시간대 설정
//        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
//
//        // 내일 날짜 구하기 (한국 시간 기준)
//        LocalDate tomorrow = LocalDate.now(koreaZoneId).plusDays(1);
//
//        // LocalDateTime 만들기
//        LocalDateTime tomorrowDateTime = LocalDateTime.of(tomorrow, LocalTime.of(hour, minute));
//
//        // DateTimeFormatter를 사용하여 LocalDateTime을 문자열로 변환
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = tomorrowDateTime.format(formatter);
//
//        return formattedDateTime;
//    }
//
//}
