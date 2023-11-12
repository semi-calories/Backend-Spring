package com.example.demo.service;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.User.Alert.AlertRecord;
import com.example.demo.domain.User.Alert.AlertSetting;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.Recommend.FastAPI.RequestRecommendAPIDto;
import com.example.demo.dto.Recommend.FastAPI.ResponseRecommendAPIDto;
import com.example.demo.dto.Recommend.Response.RecommendDto;
import com.example.demo.feign.FastApiFeign;
import com.example.demo.repository.AlertRecordRepository;
import com.example.demo.repository.AlertSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class crontestService {
    private final AlertSettingRepository alertSettingRepository;
    private final AlertRecordRepository alertRecordRepository;

    private final FastApiFeign fastApiFeign;
    private final UserService userService;
    private final DietService dietService;
    private final DBService dbService;


    /**
     * 매일 20시 00분에 실행
     */
    @Transactional
    @Scheduled(cron = "0 17 3 * * ?", zone="Asia/Seoul")
    public void findALlAlertSetting(){

        // 1. 허용여부 및 수신시간 확인
        List<AlertSetting> alertSettingList = alertSettingRepository.findAllBySetting(true);
        //[AlertSetting(id=5,
        // userCode=User(userCode=6, email=junsu@email.com, name=박지은, age=25, gender=F, image=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MnAqcAAYFAAqhadRRQAUUUUAFFFFABSUtFAETJ3FRVaqJ17igCKpEbHBqOigC1RTEORT6ACiiigAooooAKKKKACiiigDa0f/AJa/8B/rW1WLo/8Ay1/4D/WtqgD/1fQ6KKKACiiigAooooAKKKKACmscCnVFIe1AEVFFOUZNAEqLgU+iigAooooAKKKKACiiigAooooAKKKKAIHXBplTuMioKAHocGp6q1YByM0AOooooAKKKKACiiigAooooA2tH/5a/wDAf61tVi6P/wAtf+A/1raoA//W9DooooAKKKKACiiigAooooAKrvy1WKrN1NACVLGOpqKp4/u0APooooAKKKKACiuM1LWdbuNafQ/DqW/mW0QmnludxUbvuoAvOT61qeHdbGuaWL2VBDLG7xTJnISSM4YZ9O9AG/RSAgjI5BpaACimhlblSD2455p1ABRRRQAVWIwcVZqB/vGgBlTR9KhqWPvQBLRRRQAUUUUAFFFFABRRRQBtaP8A8tf+A/1rarF0f/lr/wAB/rW1QB//1/Q6KKKACiiigAooooAKKKKACqx6mrNV3+8aAG1On3agqWM8EUAS0UUUAFFFFAHnKX1noHjXU5NWmW3ivreKSKSQ7VbyvlZQTxnvimeF4n/4RXVL9lKpfTXVxGCMfu2GFOPfFeg3FpaXihLuGOZVOQJFDAH1GQam2IV2YG3GMY4x6YoA88sdcv2sbaAuLIu9vAPMUExRNHkSHJwTIRhc8A8HnNXNT1y6spUS2uhOYDCJfljVHEj7eudxJAPCDgjn0rsntraVSkkSMrKFIZQQVHQcjp7Uz7DYgq32eLKDap2L8o9BxwKAMrw7j7Lcgdry5/8ARhrfpiRxx7vLULuO5sADJ9T6mn0AFFFFABUD/eqeqxOSaAEqWPvUVTR9KAJKKKKACiiigAooooAKKKKANrR/+Wv/AAH+tbVYuj/8tf8AgP8AWtqgD//Q9DooooAKKKKACiiigAooooAKhkHOampjjIoAgp6HDUyigC1RTVORTqACiiigAooooAKKKKACiiigAooooAaxwKr1I7ZOKjoAKsKMCoVGTVigAooooAKKKKACiiigAooooA2tH/5a/wDAf61tVi6P/wAtf+A/1raoA//R9DooooAKKKKACiiigAooooAKKKKAIHGDTKsMNwxUBGOKAFU7TmpwcjIqtTlYrQBYopoIPSnUAFFFFABRRRQAUUUUAFMdscDrSM4HAqKgBKKKei5NAD0GBmpKKKACiiigAooooAKKKKACiiigDa0f/lr/AMB/rW1WLo//AC1/4D/WtqgD/9L0OiiigAooooAKKKKACiiigAooooAKYy55HWn0UAVqSp2UNUJBHWgABI6VIJPWoqKALAZT3parUUAWqTIHWq+TSUATl1HvUZcmmUUAFFFSKhPJoAaqlqnAwMCgDHApaACiiigAooooAKKKKACiiigAooooA2tH/wCWv/Af61tVi6P/AMtf+A/1raoA/9P0OiiigAooooAKKKKACiiigAooooAKKKKACkxnrS0UARmP0qMqR2qxRQBVoqyQD1puxfSgCCip9i0u1fSgCDrTghPtU1LQAwIBT6KKACiiigAooooAKKKKACiiigAooooAKKKKANrR/wDlr/wH+tbVYuj/APLX/gP9a2qAP//U9DooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDa0f/lr/wAB/rW1WLo//LX/AID/AFraoA//1fQ6KKKACiiigAooqqzSSyGONtir95h1z6CgC1RVb7O3/PaT8x/hR9nb/ntJ+Y/woAs0VW+zt/z2k/Mf4UfZ2/57SfmP8KALNFVvs7f89pPzH+FH2dv+e0n5j/CgCzRVb7O3/PaT8x/hR9nb/ntJ+Y/woAs0VW+zt/z2k/Mf4UfZ2/57SfmP8KALNFVvs7f89pPzH+FH2dv+e0n5j/CgCzRVb7O3/PaT8x/hR9nb/ntJ+Y/woAs0VW+zt/z2k/Mf4UfZ2/57SfmP8KALNFVvs7f89pPzH+FH2dv+e0n5j/CgCzRVb7O3/PaT8x/hR9nb/ntJ+Y/woAs0VW+zt/z2k/Mf4UfZ2/57SfmP8KALNFVvs7f89pPzH+FH2dv+e0n5j/CgCzRVUwSDlJnz/tYIqSGQyKdwwynBHvQBNRRRQAUUUUAbWj/8tf8AgP8AWtqsXR/+Wv8AwH+tbVAH/9b0OiiigAooooAKrW/3pv8AfP8AIVZqtb/em/3z/IUAWaKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKrQf6yX/e/pVmq0H35f97+lAFmiiigAooooA2tH/wCWv/Af61tVi6P/AMtf+A/1raoA/9f0OiiigAooooAKrW/3pv8AfP8AIVZqtb/em/3z/IUAWawdd16HQltzLEZTcyGNRvSMAhS2S0hVQMD1rerjvGkEP9mxajKZAbOZHXy9p/1h8skh0cEAMT0oAsJ4lRptOSSNIkv2mXc0qMF8pcjDISp3HjGam1vxJZaJJBFL+8eVwJFU5MUX8UrAA4VeM5xXN2yaHearpscGopfC3eZvLmUZJdMDYFjVRgjNTajY6ro9xFLY3UCnUrtYZSLRM4kycsd2WxjvQB1mo6tDYWP9oKjXEXXdEUwFxncWZlXb75rnbDxg99DYv9jZDdXCW7nejIGYMTsZGbJGO4HWku/D9xb6LJp0UYujJMsqi3SK28t1IYNhiVb5gMj9K5ptK1MmwXXoGSe81GF55Y5VG5ljdQEEQXYAB1zk0AdZq/io6ZqraZ5UA2xJL5k8/kg7yRhfkbOMc1o+Htc/t2Ceby1j8iYxZjk8xGwobcrbVyOcdK878YCKPWJLKCeS2Zo45C5kYcsT90tOi9uQF4rqPAwElvdXDMrP5mwFJWkGzAIyDJIAc56HpQB3lFFFAHPaz4ks9FljtpIp7iaRWk8u3Teyxp95zyMAVr2l7bX1nHf2r74ZUDq3qDXE68LvTPEY1tbWe7hmsXtcQJvZZN25cgdA3TNanh7T7nSvC1lpd0Ns2wIw/ul2JI/AGgDpDcKIVmKsd+MKBk8/SozewbFddzb84ABJ+Xrke1Ounmih/wBFTe3QDjj35x0rOe2cxxKI5Aqhs4ID7j3znoeaANBr2EFVUO+9dw2qTwe9WWZUG5yFHqTiseSCfykURN54jCh0bCg+/I6fQ1Lfht0AA3vk8YDZ+Xk4JH86ANMMrDKkEeop1U7DyhaosOdq5XkYOR1q5QAVnarqlpo9k9/ekiNMDCjLMScAAdyTWjXJ+MrK7vNJR7OMzPbTxzmNfvMqNkgDucdqANLSNdtNYSYxJLBLbttlhmXbIhIyMjJ6jpWgl3E6lgGHTAI5O7pj61ynhuK4u9W1TXJbeW3hvDGkSTLschByxU8jnpW/JZspYxbiFxjnkj2+g4FAGtRVe2V1hAkznJwDyQM8Z/CrFABVaD78v+9/SrNVoPvy/wC9/SgCzRRRQAUUUUAbWj/8tf8AgP8AWtqsXR/+Wv8AwH+tbVAH/9D0OiiigAooooAKrW/3pv8AfP8AIVZqtb/em/3z/IUAWaKKKADJpKWigApKWigBKWiigAooooAKSlooAKKKKACo5IopRiVQwHqM1JRQA1VVFCoAAOgFOoooAKKKKACiiigAooooAKrQffl/3v6VZqtB9+X/AHv6UAWaKKKACiiigDa0f/lr/wAB/rW1WLo//LX/AID/AFraoA//0fQ6KKKACiiigAqtb/em/wB8/wAhVmq1v96b/fP8hQBZooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqtB9+X/e/pVmq0H35f8Ae/pQBZooooAKKKKANrR/+Wv/AAH+tbVYuj/8tf8AgP8AWtqgD//S9DooooAKKKKACq1v96b/AHz/ACFWarW/3pv98/yFAFmuR8VX89i+nql29lDNOyTyoqsQojYj7ysB8wHauurH1201C9014NMlEVxvjZWLMowrhiCVBOCARQByjatK+o6PHplxLqIMlwspf9zvGzOT8qqducjA/Wucv/EXiDbdWwnKiI6hyqusgVA3lFiVCgDA2lTn1r0SOHWbnULe61K0tAIC2145pGdN4wdoKKDnpyat32iWWoSma5e4yQAVSeWNcD/ZVgP0oA42xv8AUJND1CW7a5FwL1kRbVt7A7EwgdlwEz1JAAqnbTa3BYw2uoXFzJfLe2olbeHieMycmNkA4K8OD0x6V1NrouoWukalp6SB3uJJjbGVjKFR1AQOXBzgjkHNcva+DdSS4tpp4Lf9zLHISkkacoQc/Jaqce26gD1SiiigAooooA4nXNS1mXWl0PRZ0tWjtXupJHjEm7B2qgBIwCeprX0LWDq3h+31iRQjSxbmUdNy5Bx7ZFVNb8PXd9fJqmlXYs7kQtbuWj8xWifnpkYIPINaVhpMWmaXbaRa5MUAVST1IByT+JoAuu08dpuBBkVMksOM456YqGWacWiXKOq/ICQVzknoByKtzxNNGYw5TdwSADx+NQi0/cJA7lghUg4AyF6CgBkkt1EbcOVO9gr8HqR25qee48kqoUuzkgAYHQZPJplxbNcOjCUpsO4AAHn15qC/hkl8oBSyqSTgAnpgcHAoAt2832iFZgpUNyAf/rVPVe1EwgUTgBhngADjtwOOlWKACub8U6rdaTpgksdv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, height=162.0, weight=52.0),
        // userToken=SHFDI79JDSSF,
        // setting=true, breakfastHour=7, breakfastMinute=0, launchHour=13, launchMinute=0, dinnerHour=18, dinnerMinute=30)]


        // 2. 추천 요청
        // 3. AlertRecord 저장
        List<AlertRecord> alertRecordList = new ArrayList<>();
        for(int i=0; i<alertSettingList.size(); i++){
            Long userCode = alertSettingList.get(i).getUserCode().getUserCode();
            for(int j=1; j<4; j++){
                RecommendDto recommend = requestPushRecommend(userCode, j).get(0);
                alertRecordList.add(getAlertRecord(alertSettingList.get(i), j, recommend));
            }
        }
        alertRecordRepository.saveAll(alertRecordList);
    }



    public AlertRecord getAlertRecord(AlertSetting alertSetting, int j, RecommendDto recommend ){
        if(j == 1) {
            return new AlertRecord(alertSetting.getUserCode(), alertSetting.getUserToken(),
                    getTomorrowDateTime(alertSetting.getBreakfastHour(), alertSetting.getBreakfastMinute()), alertSetting.getBreakfastHour(), alertSetting.getBreakfastMinute(),
                    j, dbService.findOne(Long.valueOf(recommend.getFoodCode())),
                    recommend.getFoodName(), recommend.getFoodKcal(),
                    recommend.getFoodCarbon(), recommend.getFoodProtein(), recommend.getFoodFat());
        }
        if(j == 2) {
            return new AlertRecord(alertSetting.getUserCode(), alertSetting.getUserToken(),
                    getTomorrowDateTime(alertSetting.getLaunchHour(), alertSetting.getLaunchMinute()), alertSetting.getLaunchHour(), alertSetting.getLaunchMinute(),
                    j, dbService.findOne(Long.valueOf(recommend.getFoodCode())),
                    recommend.getFoodName(), recommend.getFoodKcal(),
                    recommend.getFoodCarbon(), recommend.getFoodProtein(), recommend.getFoodFat());
        }
        return new AlertRecord(alertSetting.getUserCode(), alertSetting.getUserToken(),
                getTomorrowDateTime(alertSetting.getDinnerHour(), alertSetting.getDinnerMinute()), alertSetting.getDinnerHour(), alertSetting.getDinnerMinute(),
                j, dbService.findOne(Long.valueOf(recommend.getFoodCode())),
                recommend.getFoodName(), recommend.getFoodKcal(),
                recommend.getFoodCarbon(), recommend.getFoodProtein(), recommend.getFoodFat());
    }

    public List<RecommendDto> requestPushRecommend(Long UserCode, int eatTimes) {

        // DB에서 해당 정보 가져옴
        // 유저 목표 및 유저 조회
        UserGoal user = userService.findUserWithUserGoal(UserCode);

        // 유저 선호, 비선호, 기록 조회
        List<UserDietPrefer> preferDiet = dietService.findPreferByUserCode(UserCode);
        List<UserDietDislike> dislikeDiet = dietService.findDislikeByUserCode(UserCode);
        List<DietRecord> dietRecords = dietService.findDietRecordByUserCodeAndDate(UserCode, LocalDate.now());


        // FASTAPI 서버에 api 요청
        RequestRecommendAPIDto requestRecommendAPIDto =
                new RequestRecommendAPIDto(user, 1, preferDiet, dislikeDiet, dietRecords);
        System.out.println(requestRecommendAPIDto);

        ResponseRecommendAPIDto responseAPIDto = fastApiFeign.requestRecommend(requestRecommendAPIDto);

        // FASTAPI 응답 DTO로 list별로 음식 접근가능케 함 (fast api: 인덱스별로 접근)
        List<RecommendDto> recommendDtoList = IntStream.range(0, responseAPIDto.getFoodCodeList().size()) // 음식 추천 수만큼 반복
                .mapToObj(i -> new RecommendDto(
                        responseAPIDto.getFoodCodeList().get(i),
                        responseAPIDto.getFoodNameList().get(i),
                        responseAPIDto.getFoodMainCategoryList().get(i),
                        responseAPIDto.getFoodDetailedClassificationList().get(i),
                        responseAPIDto.getFoodWeightList().get(i),
                        responseAPIDto.getFoodKcalList().get(i),
                        responseAPIDto.getFoodCarbonList().get(i),
                        responseAPIDto.getFoodProteinList().get(i),
                        responseAPIDto.getFoodFatList().get(i)
                ))
                .collect(Collectors.toList());

        // 응답 DTO 생성 및 return
        return recommendDtoList;
    }

    public String getTomorrowDateTime(int hour, int minute){
        // 한국 시간대 설정
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");

        // 내일 날짜 구하기 (한국 시간 기준)
        LocalDate tomorrow = LocalDate.now(koreaZoneId).plusDays(1);

        // LocalDateTime 만들기
        LocalDateTime tomorrowDateTime = LocalDateTime.of(tomorrow, LocalTime.of(hour, minute));

        // DateTimeFormatter를 사용하여 LocalDateTime을 문자열로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = tomorrowDateTime.format(formatter);

        // 결과 출력
        //System.out.println("현재 날짜와 시간을 문자열로 변환: " + formattedDateTime);
        //log.info("한국시간?", formattedDateTime);

        return formattedDateTime;
    }

}
