package com.example.demo.service;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietPrefer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DietServiceTest {

    @Autowired DietService dietService;

    @Test
    public void prefer_목록_조회() throws Exception{
        //given
        List<UserDietPrefer> preferList = dietService.findPreferByUserCode(0L);
        //when
        System.out.println(preferList);
        //then

        assertThat(preferList.size()).isEqualTo(2);
    }

    @Test
    public void record_목록_조회() throws Exception{
        //given
        List<DietRecord> result = dietService.findDietRecordByUserCode(0L, now());
        //when
        System.out.println(result);

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}