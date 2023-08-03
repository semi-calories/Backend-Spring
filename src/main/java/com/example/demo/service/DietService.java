package com.example.demo.service;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.Diet.UserSatisfaction;
import com.example.demo.repository.DietRecordRepository;
import com.example.demo.repository.DislikeDietRepository;
import com.example.demo.repository.PreferDietRepository;
import com.example.demo.repository.UserSatisfactionRepository;
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
}
