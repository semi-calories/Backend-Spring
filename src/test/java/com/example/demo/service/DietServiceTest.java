package com.example.demo.service;

import com.example.demo.domain.User.Diet.DietRecord;
import com.example.demo.domain.User.Diet.UserDietDislike;
import com.example.demo.domain.User.Diet.UserDietPrefer;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.User.Request.RequestPreferenceSaveDto;
import com.example.demo.repository.DietRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DietServiceTest {

    @Autowired UserService userService;
    @Autowired DBService dbService;
    @Autowired DietService dietService;
    @Autowired
    DietRecordRepository dietRecordRepository;



    @Test
//    @Rollback(false)
    public void 선호음식_저장() throws Exception{
        //given
        User user = userService.findOne(1L);
        RequestPreferenceSaveDto requestPreferenceSaveDto = new RequestPreferenceSaveDto(1L, Arrays.asList(6L));

        //when
        Long savePreferCode = dietService.savePreferDiet(user,requestPreferenceSaveDto ,true);

        //then
        List<UserDietPrefer> preferList = dietService.findPreferByUserCode(1L);
        System.out.println(preferList);
        assertThat(preferList.get(1).getPreferFoodCode().getFoodCode()).isEqualTo(6l);
    }

    @Test
//    @Rollback(false)
    public void 비선호음식_저장() throws Exception{
        //given
        User user = userService.findOne(1L);
//        DietList food = dbService.findOne(3L);
        RequestPreferenceSaveDto requestPreferenceSaveDto = new RequestPreferenceSaveDto(1L, Arrays.asList(5L));

        //when
        Long savePreferCode = dietService.savePreferDiet(user,requestPreferenceSaveDto ,false);

        //then
        List<UserDietDislike> dislikeList = dietService.findDislikeByUserCode(1L);
        System.out.println(dislikeList);
        assertThat(dislikeList.get(2).getDislikeFoodName()).isEqualTo("감자옹심이");
    }


    @Test
    public void 선호음식_목록_조회() throws Exception{
        //given

        //when
        List<UserDietPrefer> preferList = dietService.findPreferByUserCode(1L);

        //then
        assertThat(preferList.get(0).getUserCode().getName()).isEqualTo("박지은");
        assertThat(preferList.get(0).getPreferFoodName()).isEqualTo("약식");

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
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void 식단_기록_목록_조회_betweenDate() throws Exception{
        //given

        //when
        List<DietRecord> result = dietService.findDietRecordByUserCodeAndDate(1L, LocalDate.of(2023,8,10));

        for (DietRecord dietRecord : result) {
            System.out.println("dietRecord = " + dietRecord);
        }
        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void 해당month_기록_get() throws Exception{
        //given
        User user = userService.findOne(1L);
        UserGoal userGoal = userService.findUserWithUserGoal(1L);

        //when
//        MultiValueMap<Integer, DietRecord> weekList = dietService.getWeekList(user, 2023, 8);
        List<List<Double>> monthList = dietService.getMonthList(userGoal ,2023);

        for (List<Double> doubles : monthList) {
            System.out.println("doubles = " + doubles);
        }

        //then
    }



}