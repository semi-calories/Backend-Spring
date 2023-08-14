package com.example.demo.service;

import com.example.demo.domain.DB.DietList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        Assertions.assertThat(findDiet.getFoodName()).isEqualTo("메밀 전병");
    }

    @Test
    public void 음식이름_검색() throws Exception{
        //given
        String name = "김치";
        //when
        List<DietList> findFoodList = dbService.findDietListByName(name);

        System.out.println("findFoodList.size() = " + findFoodList.size());
        for (DietList dietList : findFoodList) {
            System.out.println("%김치% = " + dietList.getFoodName());
        }
        //then
    }

    @Test
    public void 음식이름_빈검색() throws Exception{
        //given
        String name = "";
        //when
        List<DietList> findFoodList = dbService.findDietListByName(name);

        System.out.println("findFoodList.size() = " + findFoodList.size());
        for (DietList dietList : findFoodList) {
            System.out.println("%(글자없음)% = " + dietList.getFoodName());
        }
        //then
    }
}