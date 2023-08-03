package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;

    @Test
    public void 유저_유저목표_조회() throws Exception{
        //given
        UserGoal result = userService.findUserWithUserGoal(1L);
        //when
        System.out.println(result);
        //then
        Assertions.assertThat(result.getUserCode().getName()).isEqualTo("박지은");
    }

    @Test
    public void 유저조회() throws Exception{
        //given
        User one = userService.findOne(1L);
        //when

        //then
        Assertions.assertThat(one.getName()).isEqualTo("박지은");
    }
}