package com.example.demo.service;

import com.example.demo.domain.User.PredictUserWeight;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.domain.User.UserWeight;
import com.example.demo.dto.Record.Response.WeightDto;
import com.example.demo.dto.User.Request.RequestUserUpdateDto;
import com.example.demo.repository.PredictUserWeightRepository;
import com.example.demo.repository.UserGoalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserWeightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;
    private final UserWeightRepository userWeightRepository;

    private final PredictUserWeightRepository predictUserWeightRepository;


    /**
     * 유저 기본 정보 수정
     */
    @Transactional
    public Long userUpdate(RequestUserUpdateDto requestUserInfoUpdateDto, String userImageUrl) {

        User findUser = findOne(requestUserInfoUpdateDto.getUserCode());

        // 유저 정보 수정
        findUser.change(
                requestUserInfoUpdateDto.getName(),
                requestUserInfoUpdateDto.getEmail(),
                userImageUrl,
                requestUserInfoUpdateDto.getAge(),
                requestUserInfoUpdateDto.getGender(),
                requestUserInfoUpdateDto.getHeight(),
                requestUserInfoUpdateDto.getWeight()
        );

        // 몸무게 테이블 오늘날짜로 저장(있으면 수정 없으면 저장)
        List<UserWeight> userWeights = getUserWeight(requestUserInfoUpdateDto.getUserCode(), LocalDate.now());

        if (!userWeights.isEmpty()) {
            UserWeight userWeight = userWeights.get(0); // 첫 번째 요소 사용
            userWeight.change(requestUserInfoUpdateDto.getWeight());
            changeHarrisBenedict(requestUserInfoUpdateDto.getUserCode(), requestUserInfoUpdateDto.getWeight());
        } else {
            userWeightRepository.save(new UserWeight(findOne(requestUserInfoUpdateDto.getUserCode()), requestUserInfoUpdateDto.getWeight()));
        }

        return findUser.getUserCode();
    }



    /**
     * 몸무게 저장/수정
     */
    @Transactional
    public Long saveUserWeight(Long userCode, LocalDateTime dateTime ,Double weight) {

        User findUser = findOne(userCode);
        UserGoal findUserGoal = findUserWithUserGoal(userCode);

        // 유저 정보 modified_at 보다 이후에 저장/수정 한다면 유저 정보 갱신
        if ( findUser.getModifiedAt().isBefore(dateTime)){
            findUser.weightChange(weight);
            // 헤리스 베네딕트
            changeHarrisBenedict(userCode, weight);
        }

        // 몸무게 테이블에 저장 (있으면 수정 없으면 저장)
        List<UserWeight> weightList = getUserWeight(userCode, LocalDate.from(dateTime));
        if(!weightList.isEmpty()){
            weightList.get(0).change(weight);
        }else{
            userWeightRepository.save(new UserWeight(findOne(userCode), weight, dateTime));
        }


        return findUser.getUserCode();
    }

    /**
     * 몸무게 삭제
     */
    @Transactional
    public void deleteUserWeight(Long userCode, LocalDateTime dateTime){

        List<UserWeight> weightList = getUserWeight(userCode, LocalDate.from(dateTime));

        if(!weightList.isEmpty()){
            UserWeight weight = weightList.get(0);
            userWeightRepository.deleteById(weight.getId());

            if (LocalDate.from(weight.getTimestamp()).isEqual(LocalDate.from(dateTime))) {
                // 유저 정보가 저장된 날짜의 값을 삭제할 경우
                // 가장 최신 값으로 변경

                User findUser = findOne(userCode);
                userWeightRepository.findTopByUserCodeOrderByTimestampDesc(findUser)
                        .ifPresent(latestWeight -> {
                            findUser.weightChange(latestWeight.getWeight());
                            changeHarrisBenedict(userCode, latestWeight.getWeight());
                        });
            }
        }
    }


    /**
     * 유저 목표 정보 수정
     */
    @Transactional
    public Long userGoalUpdate(RequestUserUpdateDto requestUserUpdateDto) {

        UserGoal findGoal = findUserWithUserGoal(requestUserUpdateDto.getUserCode());

        findGoal.change(
                requestUserUpdateDto.getUserActivity(),
                requestUserUpdateDto.getUserGoal(),
                requestUserUpdateDto.getGoalWeight(),
                requestUserUpdateDto.getPeriod()
        );


        return findGoal.getUserCode().getUserCode();
    }

    /**
     * 유저 몸무게 수정
     */
    @Transactional
    public void userGoalWeightUpdate(Long userCode, Double weight, int period) {

        UserGoal findGoal = findUserWithUserGoal(userCode);

        findGoal.predictWeightChange(
                weight,
                period
        );

    }



    /**
     * 유저 단건 검색
     */
    public User findOne(Long userCode){

        return userRepository.findById(userCode)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

    }

    /**
     * 유저 및 유저 목표 조회
     */
    public UserGoal findUserWithUserGoal(Long userCode){

        return userGoalRepository.findAllWithUser(userCode)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 정보입니다.") );
    }

    /**
     * 이미 있는 유저에 HB 알고리즘 돌려 DB에 저장
      */
    @Transactional
    public void changeHarrisBenedict(Long userCode, Double weight){
        User findUser = findOne(userCode);
        UserGoal findUserGoal = findUserWithUserGoal(userCode);

        if ( findUser.getAge() != null && findUser.getHeight() != null && findUser.getWeight() !=null
                && findUserGoal.getUserGoal() != null && findUserGoal.getUserActivity()!=null){

            try {
                // 필요한 값 다 있으면 헤리스 베네딕트 값 생성
                findUserGoal.harrisBenedict(findUser, weight, ((Math.abs(findUser.getWeight()-findUserGoal.getGoalWeight()))*7700)/ findUserGoal.getGoalPeriod());
            } catch (Exception e) {
                throw new RuntimeException("헤리스 베네딕트 값을 생성할 수 없습니다.");
            }
        }

    }

    /**
     * 유저 몸무게 찾기
     */
    public List<UserWeight> getUserWeight(Long userCode, LocalDate date) {
        LocalDateTime startDatetime = LocalDateTime.of(date, LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(date, LocalTime.of(23,59,59));
        List<UserWeight> weightList = userWeightRepository.findByUserCodeWithDateBetween(userCode, startDatetime, endDatetime);
        return weightList;
    }

    /**
     * 유저 몸무게 기간별 조회
     */
    public List<UserWeight> getMonthRangeWeight(Long userCode, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {

        LocalDateTime startDatetime = LocalDateTime.of(startYear,startMonth,startDay,0,0);
        LocalDateTime endDatetime = LocalDateTime.of(endYear,endMonth,endDay,23,59);
        List<UserWeight> userWeightList = userWeightRepository.findByUserCodeWithDateBetween(userCode, startDatetime, endDatetime);
        return userWeightList;
    }


    /**
     * 예상 몸무게 저장
     */
    @Transactional
    public void savePredictWeight(Long userCode, int period){
        User findUser = findOne(userCode);
        UserGoal findUserGoal = findUserWithUserGoal(userCode);


        // 예상 몸무게 저장 들어오면 기존 값 다 삭제
        predictUserWeightRepository.deleteByUserCode(userCode);

        /**
         * 새로 예상몸무게 계산후 저장 (건강 유지 제외)
         */

        if(!findUserGoal.getUserGoal().equals("health")){
            funcPredictWeight(period, findUser, findUserGoal);
        }
    }

    /**
     * 예상 몸무게 계산
     */
    private void funcPredictWeight(int period, User findUser, UserGoal findUserGoal) {
        LocalDateTime todayLocalDate = LocalDateTime.now();

        // 기간동안 감량할 몸무게
        double predictWeight = Math.abs(findUser.getWeight() - findUserGoal.getGoalWeight()) / period;

        // 시작 몸무게 저장
        Double nowWeight = findUser.getWeight();
        predictUserWeightRepository.save(new PredictUserWeight(findUser, nowWeight,todayLocalDate));

        List<PredictUserWeight> predictUserWeightList = new ArrayList<>();

        // 기간별로 하루씩의 예상몸무게 생성 후 list에 add
        for(int i = 0; i< period; i++) {
            if (findUserGoal.getGoalWeight() > findUser.getWeight()) {
                // 찔거면
                nowWeight += predictWeight;
            } else if (findUserGoal.getGoalWeight() < findUser.getWeight()) {
                // 뺄거면
                nowWeight -= predictWeight;
            }

            predictUserWeightList.add(new PredictUserWeight(findUser, Double.parseDouble(String.format("%.1f", nowWeight)), todayLocalDate.plusDays(1)));
        }

        // list save
        predictUserWeightRepository.saveAll(predictUserWeightList);
    }


    /**
     * 예상 몸무게 조회
     */
    public List<WeightDto> getSameTimeWeight(Long userCode, List<UserWeight> monthWeight) {

        List<WeightDto> sameTimePredictWeightList = new ArrayList<>();


        // 해당 기간 유저 예상 몸무게 하루씩 조회
        monthWeight.forEach(weight->{
            predictUserWeightRepository.findByUserCodeAndTimestamp(userCode, weight.getTimestamp().toLocalDate()).ifPresentOrElse(
                    getWeight -> { // 저장된 예상 몸무게 존재
                        sameTimePredictWeightList.add(new WeightDto(weight.getTimestamp(), weight.getWeight(), getWeight.getPredictWeight()));
                    }, // 저장된 예상 몸무게 값이 없음 = 0.0으로 리턴
                    () -> sameTimePredictWeightList.add(new WeightDto(weight.getTimestamp(), weight.getWeight(), 0.0)));
        });

        // 저장된 전체 예상 몸무게 조회
        List<PredictUserWeight> predictUserWeightList = predictUserWeightRepository.findByUserCode(userCode);


        /**
         * 다이어트가 아직 안끝난 경우
         */
        // TODO
        // 몸무게 저장 마지막 날짜 < 다이어트 저장 날짜 인 경우 추가 (그 다음 저장된 날짜로 추가..)
        if (predictUserWeightList.size()>1){
            // 예상 몸무게가 저장된 경우 계산(건강 유지시 size 0임)

            LocalDateTime endWeightTime = monthWeight.get(monthWeight.size()-1).getTimestamp(); // 몸무게 맨 마지막 기록날짜
            LocalDateTime endPredictTime = predictUserWeightList.get(predictUserWeightList.size()-1).getTimestamp(); // 내 다이어트 마지막 날짜

            if(endWeightTime.isBefore(endPredictTime)){

                Duration duration = Duration.between(endWeightTime, endPredictTime);
                long days = duration.toDays();


                if(days>14){
                    // 14일 이상 차이나는경우 14일 후의 날짜 추가
                    predictUserWeightRepository.findByUserCodeAndTimestamp(userCode, endWeightTime.plusDays(14).toLocalDate()).ifPresent(
                            predictUserWeight -> {
                                sameTimePredictWeightList.add(new WeightDto(predictUserWeight.getTimestamp(),0.0, predictUserWeight.getPredictWeight()));
                            }
                    );

                    // TODO 날짜 맞추기
                    predictUserWeightRepository.findByUserCodeAndTimestamp(userCode, endWeightTime.plusDays(15).toLocalDate()).ifPresent(
                            predictUserWeight -> {
                                sameTimePredictWeightList.add(new WeightDto(predictUserWeight.getTimestamp(),0.0, predictUserWeight.getPredictWeight()));
                            }
                    );
                }else{
                    // 14일 미만이면 다이어트 맨 마지막 날짜 추가


                    predictUserWeightRepository.findByUserCodeAndTimestamp(userCode, endPredictTime.toLocalDate()).ifPresent(
                            predictUserWeight -> {
                                sameTimePredictWeightList.add(new WeightDto(predictUserWeight.getTimestamp(),0.0, predictUserWeight.getPredictWeight()));
                            }
                    );

                    // TODO 날짜 맞추기
                    predictUserWeightRepository.findByUserCodeAndTimestamp(userCode, endPredictTime.plusDays(1).toLocalDate()).ifPresent(
                            predictUserWeight -> {
                                sameTimePredictWeightList.add(new WeightDto(predictUserWeight.getTimestamp(),0.0, predictUserWeight.getPredictWeight()));
                            }
                    );
                }
            }
        }
        return sameTimePredictWeightList;
    }
}
