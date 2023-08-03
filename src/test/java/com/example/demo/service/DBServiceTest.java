package com.example.demo.service;

import com.example.demo.domain.DB.DietList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DBServiceTest {

    @Autowired DBService dbService;

    @Test
    public void 음식_검색() throws Exception{
        //given
        Long foodCode = 1L;

        //when
        DietList findDiet = dbService.findOne(foodCode);

        //then
        Assertions.assertThat(findDiet.getFoodName()).isEqualTo("가래떡");
    }
}