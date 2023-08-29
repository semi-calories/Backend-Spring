package com.example.demo.controller;

import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.User.Request.*;
import com.example.demo.dto.User.Response.ResponseUserGetDto;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final DietService dietService;




    /**
     * 회원 정보 추가 저장(수정)
     */
    @PostMapping("/updateInfo")
    public ReturnDto updateUserInfo(@RequestBody RequestUserUpdateDto requestInfoUpdateDto) throws Exception{


        // 유저 수정
        Long userCode = userService.userUpdate(requestInfoUpdateDto);

        // 유저 목표 수정
        userService.userGoalUpdate(requestInfoUpdateDto);

        // 헤리스 베네딕트 수정
        userService.changeHarrisBenedict(requestInfoUpdateDto.getUserCode());

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return returnDto;
    }


    /**
     * 회원 정보 조회
     */
    @GetMapping("/getInfo")
    public ResponseUserGetDto getInfo(Long userCode) throws Exception {

        // 기본 정보 조회
        User findUser = userService.findOne(userCode);

        // 목표 정보 조회
        UserGoal findGoal = userService.findUserWithUserGoal(userCode);

        // 응답 DTO 생성
        ResponseUserGetDto responseUserInfoGetDto = new ResponseUserGetDto(findUser, findGoal);
        return responseUserInfoGetDto;
    }

    /**
     * 선호 음식 저장
     */
    @PostMapping("/savePrefer")
    public ReturnDto savePrefer(@RequestBody RequestPreferenceSaveDto requestPreferSaveDto) throws Exception{

        // 유저 찾기
        User user = userService.findOne(requestPreferSaveDto.getUserCode());

        // 선호 음식 저장
        Long userCode = dietService.savePreferDiet(user, requestPreferSaveDto, true);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return  returnDto;
    }

    /**
     * 선호 음식 조회
     */
    @GetMapping("/getPrefer")
    public ReturnDto getPrefer(Long userCode) throws Exception{

        // 선호음식 조회
        List<UserDietPrefer> preferDiet = dietService.getPreferDiet(userCode);

        ReturnDto<List<UserDietPrefer>> returnDto = new ReturnDto<>(preferDiet);
        return  returnDto;

    }


    /**
     * 비선호 음식 저장
     */
    @PostMapping("/saveDislike")
    public ReturnDto saveDislike(@RequestBody RequestPreferenceSaveDto requestDislikeSaveDto) throws Exception{

        // 유저 찾기
        User user = userService.findOne(requestDislikeSaveDto.getUserCode());

        // 비선호음식 저장
        Long userCode = dietService.saveDislikeDiet(user, requestDislikeSaveDto, false);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return  returnDto;
    }

    /**
     * 비선호 음식 조회
     */
    @GetMapping("/getDislike")
    public ReturnDto getDislike(Long userCode) throws Exception{

        // 선호음식 조회
        List<UserDietDislike> dislikeDiet = dietService.getDislikeDiet(userCode);

        ReturnDto<List<UserDietDislike>> returnDto = new ReturnDto<>(dislikeDiet);
        return  returnDto;

    }


    @AllArgsConstructor
    @Getter
    static class ReturnDto<T>{
        private T response;
    }
}
