package com.example.demo.service;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.repository.DietRecordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DietServiceTest {

    @Autowired DietService dietService;
    @Autowired
    DietRecordRepository dietRecordRepository;

    @Test
    public void 선호음식_저장() throws Exception{
        //given

        //when

        //then
    }

    @Test
    public void 선호음식_목록_조회() throws Exception{
        //given

        //when
        List<UserDietPrefer> preferList = dietService.findPreferByUserCode(1L);

        //then
        assertThat(preferList.get(0).getUserCode().getName()).isEqualTo("박지은");
        assertThat(preferList.get(0).getPreferFoodName()).isEqualTo("가래떡");

    }

    @Test
    public void 비선호음식_목록_조회() throws Exception{
        //given

        //when
        List<UserDietDislike> dislikeList = dietService.findDislikeByUserCode(1L);

        //then
        assertThat(dislikeList.get(0).getUserCode().getName()).isEqualTo("박지은");
        assertThat(dislikeList.get(0).getDislikeFoodName()).isEqualTo("가래떡");
    }

    @Test
    public void 식단_기록_목록_전체_조회() throws Exception{
        //given

        //when
        List<DietRecord> result = dietService.findDietRecordByUserCode(1L);


        //then
        assertThat(result.size()).isEqualTo(2);
    }


    @Test
    public void 식단_기록_목록_조회_betweenDate() throws Exception{
        //given

        //when
        List<DietRecord> result = dietService.findDietRecordByUserCodeAndDate(1L, LocalDateTime.of(2023,7,31,3,23));

        //then
        assertThat(result.size()).isEqualTo(2);
    }

}