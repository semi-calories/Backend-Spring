package com.example.demo.service;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.Diet.UserSatisfaction;
import com.example.demo.domain.User.User;

import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.Record.Request.RequestWeekStatDto;
import com.example.demo.dto.Record.Request.WeekDto;
import com.example.demo.dto.User.Request.RequestPreferenceSaveDto;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietService {

    private final PreferDietRepository preferRepository;
    private final DislikeDietRepository dislikeRepository;
    private final DietRecordRepository dietRecordRepository;
    private  final UserSatisfactionRepository userSatisfactionRepository;
    private final DietListRepository dietListRepository;

    /**
     * prefer diet 조회 by user code
     */
    public List<UserDietPrefer> findPreferByUserCode(Long userCode) throws Exception{
        List<UserDietPrefer> preferDietList = preferRepository.findByUserCode(userCode);
        return preferDietList;
    }

    /**
     * dislike diet 조회 by user code
     */
    public List<UserDietDislike> findDislikeByUserCode(Long userCode) throws Exception{
        List<UserDietDislike> dislikeDietList = dislikeRepository.findByUserCode(userCode);
        System.out.println("############## dislikeDietList = " + dislikeDietList);
        return dislikeDietList;
    }

    /**
     * 전체 식단 기록 조회 by user code
     */
    public List<DietRecord> findDietRecordByUserCode(Long userCode) throws Exception{

        List<DietRecord> dietList = dietRecordRepository.findAllByUserCode(userCode);
        return dietList;
    }


    /**
     * 식단 기록 조회 by user code & date
     */
    public List<DietRecord> findDietRecordByUserCodeAndDate(Long userCode, LocalDate date) throws Exception{

        LocalDateTime startDatetime = LocalDateTime.of(date, LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.from(date), LocalTime.of(23,59,59));
        List<DietRecord> dietList = dietRecordRepository.findAllByUserCodeWithEatDateBetween(userCode, startDatetime, endDatetime);

        return dietList;
    }

    /**
     * 식단 기록 저장
     */
    @Transactional
    public Long saveFoodRecord(DietRecord dietRecord){
        dietRecordRepository.save(dietRecord);
        return dietRecord.getId();
    }


    /**
     * 유저 만족도 저장
     */
    @Transactional
    public Long saveUserSatisfaction(Long userCode, UserSatisfaction userSatisfaction){
        Optional<UserSatisfaction> satisfaction = userSatisfactionRepository.findByUserCodeAndFoodCode(userCode, userSatisfaction.getFoodCode().getFoodCode());
        if (satisfaction.isPresent()){
            // 이미 만족도 저장된 음식이면 만족도 값만 update
            satisfaction.get().updateSatisfaction(userSatisfaction.getSatisfaction());
        } // 저장안된 음식이면 만족토 객체 db에 저장
        else userSatisfactionRepository.save(userSatisfaction);
        return userSatisfaction.getId();
    }

    /**
     * 유저 만족도 조회
     */
    public Optional<UserSatisfaction> findUserSatisfaction(Long userCode, Long foodCode){
        Optional<UserSatisfaction> satisfactionList = userSatisfactionRepository.findByUserCodeAndFoodCode(userCode, foodCode);
        return satisfactionList;
    }

    /**
     * 선호 음식 저장
     */
    @Transactional
    public Long savePreferDiet(User user, RequestPreferenceSaveDto preferSaveDto, Boolean isPrefer){

        savePrefer(user, preferSaveDto, isPrefer);

        return user.getUserCode();
    }

    /**
     * 선호 음식 조회
     */
    public List<UserDietPrefer> getPreferDiet(Long userCode){
        return preferRepository.findByUserCode(userCode);
    }

    /**
     * 비선호 음식 저장
     */
    @Transactional
    public Long saveDislikeDiet(User user, RequestPreferenceSaveDto dislikeSaveDto, Boolean isPrefer){

        savePrefer(user, dislikeSaveDto, isPrefer);
        return user.getUserCode();
    }

    /**
     * 비선호 음식 조회
     */
    public List<UserDietDislike> getDislikeDiet(Long userCode){
        return dislikeRepository.findByUserCode(userCode);
    }

    /**
     * 한달동안 기록 get
     */
    public MultiValueMap<Integer,DietRecord> getWeekList(User user, int year, int month){
        // 해당 달 식단 기록 get
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,1);
        List<DietRecord> dietRecordList = dietRecordRepository.findAllByUserCodeWithEatDateBetween(user.getUserCode(), LocalDateTime.of(year, month, 1, 0, 0), LocalDateTime.of(year, month, cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59));

        // 뽑아온 list에서 각 주간별로 탄단지 sum
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        // 한 주의 시작 요일 설정
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 첫 주를 계산할 때 최소로 있어야 하는 날짜 수 설정
        calendar.setMinimalDaysInFirstWeek(4);

        // 해당 월의 몇 주차인지 계산
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

        MultiValueMap<Integer,DietRecord> weekMap= new LinkedMultiValueMap<>();

        return weekMap;
    }

    protected void savePrefer(User user, RequestPreferenceSaveDto preferSaveDto, Boolean isPrefer) {
        // 삭제 대신 업데이트를 진행하기 위한 리스트 생성
        List<UserDietPrefer> prefersToUpdate = new ArrayList<>();
        List<UserDietDislike> dislikeToUpdate = new ArrayList<>();

        preferSaveDto.getFoodList().stream() // 선호 음식 리스트 스트림 생성
                .map(dietListRepository::findById) // 리스트에서 하나씩 DB(DietList)에서 객체 찾기
                .forEach(findDiet -> { // 각 객체에 대해
                    if (findDiet.isPresent()) {
                        // 존재한다면
                        if (isPrefer == Boolean.TRUE) {
                            prefersToUpdate.add(new UserDietPrefer(user, findDiet.get(), findDiet.get().getFoodName()));
                        } else {
                            dislikeToUpdate.add(new UserDietDislike(user, findDiet.get(), findDiet.get().getFoodName()));

                        }
                    } else {
                        throw new IllegalStateException("존재하지 않는 정보입니다.");
                    }
                });

        if (isPrefer == Boolean.TRUE) {
            preferRepository.deleteByUserCode(user.getUserCode());
            preferRepository.saveAll(prefersToUpdate);
        } else {
            dislikeRepository.deleteByUserCode(user.getUserCode());
            dislikeRepository.saveAll(dislikeToUpdate);
        }
    }

    /**
     * 1년동안 식단 통계 get
     */
    public List<List<Double>> getMonthList(UserGoal userGoal, int year){


        // 해당 달 식단 기록 get
        List<DietRecord> dietRecordList = dietRecordRepository.findAllByUserCodeWithEatDateBetween(userGoal.getUserCode().getUserCode(), LocalDateTime.of(year, 1, 1, 0, 0), LocalDateTime.of(year, 12, 31, 23, 59));

        // 각 월에 대한 식단 기록 map 저장 key:월(중복 o) value:식단 기록
        MultiValueMap<Integer, DietRecord> monthMap = getMonthDietRecord(dietRecordList);

        // 각 월에 대해 탄단지 sum
        List<List<Double>> monthList = getMonthKcalSum(monthMap);

        // 각 월에 대해 -> `탄단지 sum 값/헤리스 베네틱트 kcal*해당 30(31)`
        List<List<Double>> monthRecordList = getMonthRecordList(userGoal, year, monthList);

        return monthRecordList;
    }

    private static MultiValueMap<Integer, DietRecord> getMonthDietRecord(List<DietRecord> dietRecordList) {
        MultiValueMap<Integer,DietRecord> monthMap= new LinkedMultiValueMap<>();
        dietRecordList.stream()
                .forEach(dl->{
                    monthMap.add(dl.getEatDate().getMonthValue(), dl);
                });
        return monthMap;
    }

    private static List<List<Double>> getMonthKcalSum(MultiValueMap<Integer, DietRecord> monthMap) {

        List<List<Double>> monthList = new ArrayList<>();
        for(int month =1; month<=12; month++){

            // monthMap에서 month 키에 대한 값이 없으면 빈 리스트 return
            List<DietRecord> dietRecords = monthMap.getOrDefault(month, Collections.emptyList());

            // 각 month에 대한 탄단지kcal 값 sum
            double sumCarbo = dietRecords.stream().mapToDouble(DietRecord::getFoodCarbo).sum();
            double sumProtein = dietRecords.stream().mapToDouble(DietRecord::getFoodProtein).sum();
            double sumFat = dietRecords.stream().mapToDouble(DietRecord::getFoodFat).sum();

            // sum값 리스트에 추가
            monthList.add(Arrays.asList( sumCarbo, sumProtein, sumFat));
        }

        return monthList;
    }

    private static List<List<Double>> getMonthRecordList(UserGoal userGoal, int year, List<List<Double>> monthList) {
        Calendar cal = Calendar.getInstance();
        List<List<Double>> monthRecordList = new ArrayList<>();

        for(int month=0;month<=11;month++){
            cal.set(year,month,1);
            // month[kcal, 탄, 단, 지]
            // 각 탄단지 sum get / 한달동안 먹을양
            // 소수 첫째자리까지 반환

            double carboResult =Double.parseDouble(String.format("%.1f", (monthList.get(month).get(0)*4) *100/ (userGoal.getKcal() * cal.getActualMaximum(Calendar.DAY_OF_MONTH))));
            double proteinResult =Double.parseDouble(String.format("%.1f", (monthList.get(month).get(1)*4) *100/ (userGoal.getKcal() * cal.getActualMaximum(Calendar.DAY_OF_MONTH))));
            double fatResult =Double.parseDouble(String.format("%.1f", (monthList.get(month).get(2)*9) *100/ (userGoal.getKcal() * cal.getActualMaximum(Calendar.DAY_OF_MONTH))));

            monthRecordList.add(Arrays.asList(carboResult,proteinResult,fatResult));
        }
        return monthRecordList;
    }

    /**
     * 한달동안 식단 통계 get
     */
    public List<List<Double>> getWeekList(UserGoal userGoal, RequestWeekStatDto requestWeekStatDto){

        // 해당 달 식단 기록 get
        List<WeekDto> weekList = requestWeekStatDto.getCalculateWeek();
        List<List<DietRecord>> weekRecordList = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        // 시작일과 끝일 사이의 식단 기록 가져와 list add
        weekList.forEach(list -> {
            String[] listSplit = list.getStart().split("-");
            int year = Integer.parseInt(listSplit[0]);
            int month = Integer.parseInt(listSplit[1]);
            int endDay = Integer.parseInt(list.getEnd().split("-")[2]);
            int startDay = Integer.parseInt(listSplit[2]);

            temp.add(endDay-startDay+1);
            weekRecordList.add(dietRecordRepository.findAllByUserCodeWithEatDateBetween(userGoal.getUserCode().getUserCode(), LocalDateTime.of(year, month, startDay, 0, 0), LocalDateTime.of(year, month, endDay, 23, 59)));
        });

        // 각 주에 대해 탄단지 sum
        List<List<Double>> weekSumList = getWeekKcalSum(weekRecordList);

        // 각 주에 대해 -> `sum 값/헤리스 베네틱트*해당 주(endDay-startDay+1)`
        List<List<Double>> weekRecordResult = getWeekRecordList(userGoal, weekSumList, temp);

        return weekRecordResult;

    }

    private static List<List<Double>> getWeekKcalSum(List<List<DietRecord>> weekRecordList) {
        List<List<Double>> weekSumList = new ArrayList<>();

        weekRecordList.forEach(recordList -> {
            double sumCarbo = recordList.stream().mapToDouble(DietRecord::getFoodCarbo).sum();
            double sumProtein = recordList.stream().mapToDouble(DietRecord::getFoodProtein).sum();
            double sumFat = recordList.stream().mapToDouble(DietRecord::getFoodFat).sum();

            weekSumList.add(Arrays.asList( sumCarbo, sumProtein, sumFat));
        });
        return weekSumList;
    }

    private static List<List<Double>> getWeekRecordList(UserGoal userGoal, List<List<Double>> weekSumList, List<Integer> temp) {

        List<List<Double>> weekRecordList = new ArrayList<>();
        // 각 주에 대해 -> `sum 값/헤리스 베네틱트*해당 주(endDay-startDay+1)`
        for(int i = 0; i< temp.size(); i++){
            double carboResult = Double.parseDouble(String.format("%.1f", (weekSumList.get(i).get(0) *100/ (userGoal.getKcal() * temp.get(i)))));
            double proteinResult = Double.parseDouble(String.format("%.1f", (weekSumList.get(i).get(1) *100/ (userGoal.getKcal() * temp.get(i)))));
            double fatResult = Double.parseDouble(String.format("%.1f", (weekSumList.get(i).get(2) *100/ (userGoal.getKcal() * temp.get(i)))));
            weekRecordList.add(Arrays.asList(carboResult,proteinResult,fatResult));
        }
        return weekRecordList;
    }
}
