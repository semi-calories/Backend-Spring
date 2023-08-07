package com.example.demo.service;

import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.Diet.UserSatisfaction;
import com.example.demo.domain.User;
import com.example.demo.dto.User.Request.RequestDislikeSaveDto;

import com.example.demo.dto.User.Request.RequestPreferenceSaveDto;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
    public List<DietRecord> findDietRecordByUserCodeAndDate(Long userCode, LocalDateTime date) throws Exception{

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.from(date), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.from(date), LocalTime.of(23,59,59));
        List<DietRecord> dietList = dietRecordRepository.findAllByUserCodeWithEatDateBetween(userCode, startDatetime, endDatetime);
        return dietList;
    }

    /**
     * 식단 기록 저장 by text
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
    public Long saveUserSatisfaction(UserSatisfaction userSatisfaction){
        userSatisfactionRepository.save(userSatisfaction);
        return userSatisfaction.getId();
    }

    /**
     * 선호 음식 저장
     */
    @Transactional
    public Long savePreferDiet(User user, RequestPreferenceSaveDto preferSaveDto, Boolean isPrefer){

        extracted(user, preferSaveDto, isPrefer);

        return user.getUserCode();
    }

    /**
     * 비선호 음식 저장
     */
    @Transactional
    public Long saveDislikeDiet(User user, RequestPreferenceSaveDto dislikeSaveDto, Boolean isPrefer){

        extracted(user, dislikeSaveDto, isPrefer);
        return user.getUserCode();
    }

    @Transactional
    protected void extracted(User user, RequestPreferenceSaveDto preferSaveDto, Boolean isPrefer) {
        preferSaveDto.getFoodList().stream() // 선호 음식 리스트 스트림 생성
                .map(dietListRepository::findById) //리스트에서 하나씩 DB에서 객체 찾기
                .forEach(findDiet -> { // 각 객체에 대해
                    if(findDiet.isPresent()) {
                        // 존재한다면
                        if(isPrefer == Boolean.TRUE){
                            //prefer DB에 저장
                            preferRepository.save(new UserDietPrefer(user, findDiet.get(), findDiet.get().getFoodName()));
                        }
                        // dislike DB에 저장
                        else dislikeRepository.save(new UserDietDislike(user, findDiet.get(), findDiet.get().getFoodName()));
                    }
                    else{
                        throw new IllegalStateException("존재하지 않는 정보입니다.");
                    }
                });
    }
}
