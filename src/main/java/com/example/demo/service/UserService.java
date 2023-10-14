package com.example.demo.service;

import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.domain.User.UserWeight;
import com.example.demo.dto.User.Request.RequestUserUpdateDto;
import com.example.demo.repository.UserGoalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserWeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;
    private final UserWeightRepository userWeightRepository;


    /**
     * 유저 기본 정보 수정
     */
    @Transactional
    public Long userUpdate(RequestUserUpdateDto requestUserInfoUpdateDto) throws Exception{

        User findUser = findOne(requestUserInfoUpdateDto.getUserCode());

        // 유저 정보 수정
        findUser.change(
                requestUserInfoUpdateDto.getName(),
                requestUserInfoUpdateDto.getEmail(),
                requestUserInfoUpdateDto.getImage(),
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
    public Long saveUserWeight(Long userCode, LocalDateTime dateTime ,Double weight) throws Exception{

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
    public void deleteUserWeight(Long userCode, LocalDateTime dateTime) throws Exception {

        List<UserWeight> weightList = getUserWeight(userCode, LocalDate.from(dateTime));

        if(!weightList.isEmpty()){
            UserWeight weight = weightList.get(0);
            userWeightRepository.deleteById(weight.getId());

            if (LocalDate.from(weight.getTimestamp()).isEqual(LocalDate.from(dateTime))) {
                // 유저 정보가 저장된 날짜의 값을 삭제할 경우
                // 가장 최신 값으로 변경

                User findUser = findOne(userCode);
                UserGoal findUserGoal = findUserWithUserGoal(userCode);
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
    public Long userGoalUpdate(RequestUserUpdateDto requestUserUpdateDto) throws Exception {

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
                findUserGoal.harrisBenedict(findUser, weight, ((Math.abs(findUser.getWeight()-findUserGoal.getGoalWeight()))*9000)/ findUserGoal.getGoalPeriod());
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
     * 유저 몸무게 3개월치
     */
    public List<UserWeight> getMonthRangeWeight(Long userCode, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {

        LocalDateTime startDatetime = LocalDateTime.of(startYear,startMonth,startDay,0,0);
        LocalDateTime endDatetime = LocalDateTime.of(endYear,endMonth,endDay,23,59);
        List<UserWeight> userWeightList = userWeightRepository.findByUserCodeWithDateBetween(userCode, startDatetime, endDatetime);
        return userWeightList;
    }


}
